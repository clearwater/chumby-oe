# Builds the boot partition for a chumby Falconwing-based platform.
# Note that bootstream-chumby.bin is a binary-blob combination of
# chumby_stub, and the Freescale-provided bootlets that set up power,
# clocks, and RAM.  It is provided as a blob because Freescale provides
# elftosb2 as a blob.  If you would like to recreate these from scratch and
# are on an x86 Linux machine, you'll need the following files:
#   http://files.chumby.com/source/falconwing/bootloader/elftosb2;name=elftosb2
#   http://files.chumby.com/source/falconwing/bootloader/falconwing_chumby_sb.db;name=chumbyconfig
#   http://files.chumby.com/source/falconwing/bootloader/falconwing_factory_sb.db;name=factoryconfig

SECTION = "bootloaders"
PRIORITY = "optional"
LICENSE = "GPLv2"
DEPENDS = "chumby-bootshell chumby-blobs-falconwing config-util-native virtual/kernel"
DEPENDS_${PN} = "chumby-bootshell chumby-blobs-falconwing config-util-native virtual/kernel"
PROVIDES = "virtual/bootloader virtual/chumby-bootimage"
RPROVIDES = "virtual/bootloader virtual/chumby-bootimage"
PR = "r8"
PV = "1.0"

SRC_URI = "file://bootstream-chumby.bin"

COMPATIBLE_MACHINE = "chumby-falconwing"

FILES_${PN} = "/boot"



do_install () {
    install -d ${D}/boot
    install -d ${DEPLOY_DIR_IMAGE}
    install -m 0755 ${WORKDIR}/bootstream-chumby.bin ${DEPLOY_DIR_IMAGE}
}

do_deploy () {

    config_util --cmd=create \
        --mbr=/dev/zero \
        --configname=falconwing \
        --build_ver=1000 --force --pad \
        --blockdef=${DEPLOY_DIR_IMAGE}/chumby_shell.bin,215040,boot,1,0,0,0 \
        --blockdef=${DEPLOY_DIR_IMAGE}/4_1.bin,153600,img1,1,0,0,0 \
        --blockdef=${DEPLOY_DIR_IMAGE}/4_2.bin,153600,img2,1,0,0,0 \
        --blockdef=${DEPLOY_DIR_IMAGE}/5_1.bin,153600,img3,1,0,0,0 \
        --blockdef=${DEPLOY_DIR_IMAGE}/5_2.bin,153600,img4,1,0,0,0 \
        --blockdef=${DEPLOY_DIR_IMAGE}/5_3.bin,153600,img5,1,0,0,0 \
        --blockdef=${DEPLOY_DIR_IMAGE}/zImage-${MACHINE}.bin,4194304,krnA,1,0,0,0 \
        --blockdef=${DEPLOY_DIR_IMAGE}/zImage-${MACHINE}.bin,4194304,krnB,1,0,0,0 \
        > ${DEPLOY_DIR_IMAGE}/config_block.bin


    rm -f ${DEPLOY_DIR_IMAGE}/boot-${MACHINE}.bin
    touch ${DEPLOY_DIR_IMAGE}/boot-${MACHINE}.bin
    dd conv=notrunc of=${DEPLOY_DIR_IMAGE}/boot-${MACHINE}.bin seek=4 count=64 if=${DEPLOY_DIR_IMAGE}/bootstream-chumby.bin
    dd conv=notrunc of=${DEPLOY_DIR_IMAGE}/boot-${MACHINE}.bin seek=96 count=32 if=${DEPLOY_DIR_IMAGE}/config_block.bin 
    dd conv=notrunc of=${DEPLOY_DIR_IMAGE}/boot-${MACHINE}.bin seek=128 count=420 if=${DEPLOY_DIR_IMAGE}/chumby_shell.bin
    dd conv=notrunc of=${DEPLOY_DIR_IMAGE}/boot-${MACHINE}.bin seek=548 count=300 if=${DEPLOY_DIR_IMAGE}/4_1.bin 
    dd conv=notrunc of=${DEPLOY_DIR_IMAGE}/boot-${MACHINE}.bin seek=848 count=300 if=${DEPLOY_DIR_IMAGE}/4_2.bin
    dd conv=notrunc of=${DEPLOY_DIR_IMAGE}/boot-${MACHINE}.bin seek=1148 count=300 if=${DEPLOY_DIR_IMAGE}/5_1.bin 
    dd conv=notrunc of=${DEPLOY_DIR_IMAGE}/boot-${MACHINE}.bin seek=1448 count=300 if=${DEPLOY_DIR_IMAGE}/5_2.bin 
    dd conv=notrunc of=${DEPLOY_DIR_IMAGE}/boot-${MACHINE}.bin seek=1748 count=300 if=${DEPLOY_DIR_IMAGE}/5_3.bin
    dd conv=notrunc of=${DEPLOY_DIR_IMAGE}/boot-${MACHINE}.bin seek=2048 count=8192 if=${DEPLOY_DIR_IMAGE}/zImage-${MACHINE}.bin
    dd conv=notrunc of=${DEPLOY_DIR_IMAGE}/boot-${MACHINE}.bin seek=10240 count=8192 if=${DEPLOY_DIR_IMAGE}/zImage-${MACHINE}.bin
#    package_stagefile_shell ${DEPLOY_DIR_IMAGE}/boot-${MACHINE}.bin
}
addtask deploy before do_build after do_compile
