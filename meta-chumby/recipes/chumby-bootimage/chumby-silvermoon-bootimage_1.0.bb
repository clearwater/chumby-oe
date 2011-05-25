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
DEPENDS = "virtual/bootloader virtual/kernel chumby-blobs config-util-native"
PROVIDES = "virtual/chumby-bootimage"
RPROVIDES_${PN} = "virtual/chumby-bootimage"
PR = "r2"

COMPATIBLE_MACHINE = "chumby-silvermoon"

FILES_${PN} = "/boot"

CNPLATFORM = "${@bb.data.getVar('MACHINE', d, 1).replace('chumby-', '')}"



#do_install () {
#    install -d ${D}/boot
#    install -m 0755 ${WORKDIR}/falconwing_chumby_sb.db ${D}/boot/falconwing_chumby_sb.db
#}

do_deploy () {

    install -d ${DEPLOY_DIR_IMAGE}

    config_util --cmd=create \
        --mbr=/dev/zero \
        --configname=${CNPLATFORM} \
        --build_ver=1000 --force --pad \
        --blockdef=${DEPLOY_DIR_IMAGE}/u-boot-${MACHINE}.bin,1507328,u-bt,1,0,0,0 \
        --blockdef=${DEPLOY_DIR_IMAGE}/zImage-${MACHINE}.bin,3932160,krnA,1,0,0,0 \
        --blockdef=${DEPLOY_DIR_IMAGE}/zImage-${MACHINE}.bin,3932160,krnB,1,0,0,0 \
        > ${DEPLOY_DIR_IMAGE}/config_block.bin


    rm -f ${DEPLOY_DIR_IMAGE}/boot-${MACHINE}.bin
    touch ${DEPLOY_DIR_IMAGE}/boot-${MACHINE}.bin
    dd conv=notrunc of=${DEPLOY_DIR_IMAGE}/boot-${MACHINE}.bin seek=0 count=96 if=${DEPLOY_DIR_IMAGE}/obm.bin
    dd conv=notrunc of=${DEPLOY_DIR_IMAGE}/boot-${MACHINE}.bin seek=96 count=32 if=${DEPLOY_DIR_IMAGE}/config_block.bin 
    dd conv=notrunc of=${DEPLOY_DIR_IMAGE}/boot-${MACHINE}.bin seek=128 count=2944 if=${DEPLOY_DIR_IMAGE}/u-boot-${MACHINE}.bin
    dd conv=notrunc of=${DEPLOY_DIR_IMAGE}/boot-${MACHINE}.bin seek=3072 count=7680 if=${DEPLOY_DIR_IMAGE}/zImage-${MACHINE}.bin
    dd conv=notrunc of=${DEPLOY_DIR_IMAGE}/boot-${MACHINE}.bin seek=10752 count=7680 if=${DEPLOY_DIR_IMAGE}/zImage-${MACHINE}.bin

    package_stagefile_shell ${DEPLOY_DIR_IMAGE}/boot-${MACHINE}.bin
}
addtask deploy before do_build after do_compile
