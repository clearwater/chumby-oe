#include <stdint.h>
#include <unistd.h>
#include <stdlib.h>
#include <stdio.h>
#include <fcntl.h>
#include <string.h>

struct part_args {
    uint32_t start;
    uint32_t size;
    uint32_t type;
    char *file;
};

struct part_entry {
    uint8_t status;
    uint8_t chs1;
    uint8_t chs2;
    uint8_t chs3;
    uint8_t type;
    uint8_t chs4;
    uint8_t chs5;
    uint8_t chs6;
    uint32_t lba_address;
    uint32_t lba_size;
} __attribute__((__packed__));

struct mbr {
    uint8_t code[440];                  /* 0   - 440 */
    uint8_t disk_signature[4];          /* 440 - 444 */
    uint8_t reserved[2];                /* 444 - 446 */
    struct part_entry partitions[4];    /* 446 - 510 */
    uint8_t signature[2];               /* 510 - 512 */
} __attribute__((__packed__));

struct ebr {
    uint8_t code[446];
    struct part_entry partition;
    struct part_entry next_partition;
    uint8_t reserved[31];
    uint8_t signature[2];
} __attribute__((__packed__));

int read_mbr(int fd, struct mbr *mbr) {
    if(read(fd, mbr, sizeof(struct mbr)) != sizeof(struct mbr)) {
        perror("Couldn't read MBR");
        return 1;
    }
    return 0;
}

int read_ebr(int fd, struct mbr *mbr, struct ebr *ebr, int ebr_records) {
    int ebr_count = 0;
    uint64_t ebr_offset = 0;
    int i;

    bzero(ebr, ebr_records*sizeof(struct ebr));

    /* Locate first EBR */
    for(i=0; i<4; i++)
        if(mbr->partitions[i].type == 0x05)
            break;
    if(mbr->partitions[i].type != 0x05)
        return 0;


    /* Read first EBR, which is referenced as an offset from the MBR */
    lseek(fd, mbr->partitions[i].lba_address*512, SEEK_SET);
    if(read(fd, &ebr[ebr_count], sizeof(struct ebr)) != sizeof(struct ebr)) {
        perror("Couldn't read EBR");
        return 0;
    }
    ebr_offset = mbr->partitions[i].lba_address*512;

    /* Now read subsequent EBR partitions */
    while(ebr[ebr_count].next_partition.lba_address) {
        fprintf(stderr, "Looped.  ebr[%d].next_partition.lba_address = 0x%08x\n",
                ebr_count, ebr[ebr_count].next_partition.lba_address);
        lseek(fd, (ebr_offset + ebr[ebr_count].next_partition.lba_address)*512, SEEK_SET);
        ebr_offset += (ebr[ebr_count].next_partition.lba_address)*512;
        if(read(fd, &ebr[++ebr_count], sizeof(struct ebr)) != sizeof(struct ebr)) {
            perror("Couldn't read EBR");
            return 0;
        }
    }

    ebr_count++;
    return ebr_count;
}

static void print_help(char *cmd) {
    fprintf(stderr,
"Usage: %s -a first_partition -a second_partition ... -o output_file\n"
"\n"
"    A partition definition follows the form of size:type:filename\n"
"\n"
"    The \"size\" parameter is in bytes, and supports 'B', 'M', 'K', and 'G' suffixes.\n"
"    If no suffix is supplied, bytes are assumed.  'B' INDICATES BLOCKS NOT BYTES.\n"
"    The \"type\" parameter is an 8-bit partition ID.\n"
"    If necessary, an EBR will be created to house extra partitions (NOT SUPPORTED YET)\n",
cmd);
}

static int process_partition(char *txt, struct part_args *part) {
    char *ptr = txt;
    char tmp[512];
    int multiplier;

    /* Locate the "length" record */
    for(; *ptr && *ptr != ':'; ptr++);
    if(!*ptr) {
        fprintf(stderr, "Record length not found.\n");
        return 1;
    }
    bzero(tmp, sizeof(tmp));
    strncpy(tmp, txt, ptr-txt);
    multiplier = 1;
    if(tmp[strlen(tmp)-1] == 'K' || tmp[strlen(tmp)-1] == 'k') {
        multiplier = 1024;
        tmp[strlen(tmp)-1] = '\0';
    }
    else if(tmp[strlen(tmp)-1] == 'M' || tmp[strlen(tmp)-1] == 'm') {
        multiplier = 1024*1024;
        tmp[strlen(tmp)-1] = '\0';
    }
    else if(tmp[strlen(tmp)-1] == 'G' || tmp[strlen(tmp)-1] == 'g') {
        multiplier = 1024*1024*1024;
        tmp[strlen(tmp)-1] = '\0';
    }
    else if(tmp[strlen(tmp)-1] == 'B' || tmp[strlen(tmp)-1] == 'b') {
        multiplier = 512;
        tmp[strlen(tmp)-1] = '\0';
    }
    part->size = strtol(tmp, NULL, 0) * multiplier / 512;
    ptr++;
    txt = ptr;


    /* Locate the "type" record */
    for(; *ptr && *ptr != ':'; ptr++);
    if(!*ptr) {
        fprintf(stderr, "Record type not found\n");
        return 1;
    }
    bzero(tmp, sizeof(tmp));
    strncpy(tmp, txt, ptr-txt);
    part->type = strtol(tmp, NULL, 0);
    ptr++;
    txt = ptr;


    /* Locate the "filename" record */
    for(; *ptr && *ptr != ':'; ptr++);
    bzero(tmp, sizeof(tmp));
    strncpy(tmp, txt, ptr-txt);
    part->file = malloc(strlen(tmp)+1);
    strcpy(part->file, tmp);


    /* Make sure the file actually opened */
    int fd = open(part->file, O_RDONLY);
    if(fd < 0) {
        char tmp[2048];
        snprintf(tmp, sizeof(tmp), "Unable to open partition file \"%s\"",
                part->file);
        perror(tmp);
        return 1;
    }
    close(fd);


    return 0;
}


static int generate_mbr_ebr(int part_count, struct part_args *part,
        struct mbr *mbr, struct ebr *ebr) {
    int i;

    /*
     * We calculate addresses on the fly.  Keep a running count as we
     * generate the MBR and EBR.
     */
    uint64_t running_address = 4;

    bzero(mbr, sizeof(struct mbr));
    mbr->signature[0]           = 0x55;
    mbr->signature[1]           = 0xAA;

    /* Make first partition bootable */
    mbr->partitions[0].status   = 0x80;


    struct part_entry partitions[4];    /* 446 - 510 */
    for(i=0; i<4; i++) {
        if(part[i].size) {
            part[i].start = running_address;

            mbr->partitions[i].lba_address = part[i].start;
            mbr->partitions[i].lba_size    = part[i].size;
            mbr->partitions[i].type        = part[i].type;

            running_address += part[i].size;
        }
    }

    /* Now do EBRs */
    /* XXX Fix this so that it actually works! */
    part += 4;
    for(i=0; i<60; i++) {
        if(part[i].size) {
            part[i].start = running_address;

            ebr[i].partition.lba_address = part[i].start+0x20;
            ebr[i].partition.lba_size    = part[i].size;
            ebr[i].partition.type         = part[i].type;

            running_address += part[i].size;
        }
    }
    return 0;
}

int main(int argc, char **argv) {
    struct mbr mbr;
    struct ebr ebr[60];
    struct part_args part_args[64];
    char *cmd = argv[0];
    char *outfile = NULL;
    int part_count = 0;
    int mbr_ebr_index = 0;
    int i;
    int has_ebr = 0;

    bzero(&mbr, sizeof(mbr));
    bzero(ebr, sizeof(ebr));
    bzero(part_args, sizeof(part_args));


    /* Short circuit for people looking for help */
    if(argc < 2 || strstr(argv[1], "help") || !strcmp(argv[1], "-h")) {
        print_help(argv[0]);
        return 1;
    }

    /* Manual argument processing, as we have lots of args */
    argc--;
    argv++;
    while(argc > 0) {
        if(!strcmp(argv[0], "-o")) {
            argv++;
            argc--;
            outfile = argv[0];
        }
        else if(!strcmp(argv[0], "-a")) {
            argv++;
            argc--;

            if(process_partition(argv[0], &part_args[part_count++])) {
                fprintf(stderr, "Partition definition \"%s\" not valid.\n", argv[0]);
                print_help(cmd);
                return 1;
            }
        }
        //if(argc <= 1 || *argv[0] != '-') {
        else {
            fprintf(stderr, "Argument %s not recognized.\n", argv[0]);
            print_help(cmd);
            return 1;
        }

        argv++;
        argc--;
    }


    /* Ensure a file was specified on the command line */
    if(!outfile) {
        print_help(cmd);
        return 1;
    }


    if(generate_mbr_ebr(part_count, part_args, &mbr, ebr)) {
        fprintf(stderr, "Invalid arguments\n");
        print_help(cmd);
        return 1;
    }


    unlink(outfile);
    fprintf(stderr, "Opening output file %s\n", outfile);
    int fd = open(outfile, O_WRONLY | O_CREAT, 0755);
    if(fd < 0) {
        perror("Couldn't open rom");
        return 1;
    }


    lseek(fd, 0, SEEK_SET);

    write(fd, &mbr, sizeof(mbr));


    /* Print out what we'd make normally */
    for(i=0; i<4; i++) {
        if(mbr.partitions[i].type == 0x05) {
            has_ebr = 1;
            mbr_ebr_index = i;
            continue;
        }
        if(mbr.partitions[i].lba_address && mbr.partitions[i].lba_size) {
            char temp_name[512];
            uint64_t bytes_read;
            char buffer[512];
            int part_fd;


            fprintf(stderr, "Partition %d type: 0x%02x\n", i, mbr.partitions[i].type);
            fprintf(stderr, "Partition %d start: %d\n", i, mbr.partitions[i].lba_address);
            fprintf(stderr, "Partition %d size: %d\n", i, mbr.partitions[i].lba_size);


            part_fd = open(part_args[i].file, O_RDONLY);
            if(part_fd < 0) {
                char tmp[512];
                snprintf(tmp,
                        sizeof(tmp),
                        "Unable to open '%s' for reading",
                        part_args[i].file);
                perror(tmp);
                return 1;
            }

            bytes_read = 0;
            lseek(fd, mbr.partitions[i].lba_address*512, SEEK_SET);
            while(bytes_read < mbr.partitions[i].lba_size*512) {
                int bytes_to_copy = 1024*1024*1;
                if(bytes_read + bytes_to_copy > mbr.partitions[i].lba_size*512)
                    bytes_to_copy = mbr.partitions[i].lba_size*512 - bytes_read;
                char buffer[bytes_to_copy];

                read(part_fd, buffer, bytes_to_copy);
                write(fd, buffer, bytes_to_copy);
                bytes_read += bytes_to_copy;
            }
            close(part_fd);
        }
    }

    if(has_ebr) {
        uint64_t ebr_offset = mbr.partitions[mbr_ebr_index].lba_address*512;
        i = 0;
        while(1) {
            char temp_name[512];
            uint64_t bytes_read;
            char buffer[512];
            int part_fd;


            fprintf(stderr, "Extended Partition %d type: 0x%02x\n", i,
                    ebr[i].partition.type);
            fprintf(stderr, "Extended Partition %d start: %d\n", i,
                    ebr[i].partition.lba_address);
            fprintf(stderr, "Extended Partition %d size: %d\n", i,
                    ebr[i].partition.lba_size);


            /*
            snprintf(temp_name, sizeof(temp_name)-1, "%s%d", prefix, i+1+4);
            part_fd = open(temp_name, O_WRONLY | O_CREAT, 0755);
            if(part_fd < 0) {
                perror("Unable to open file for writing");
                return 1;
            }

            bytes_read = 0;
            ebr_offset += ebr[i].partition.lba_address*512;
            lseek(fd, ebr_offset, SEEK_SET);
            while(bytes_read < ebr[i].partition.lba_size*512) {
                read(part_fd, buffer, sizeof(buffer));
                write(fd, buffer, sizeof(buffer));
                bytes_read += sizeof(buffer);
            }
            close(part_fd);
            */


            if(!ebr[i].next_partition.lba_size && !ebr[i].next_partition.lba_address)
                break;
            i++;
        }
    }

    return 0;
}
