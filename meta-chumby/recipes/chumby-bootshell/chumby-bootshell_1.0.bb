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
DEPENDS = "virtual/kernel chumby-blobs-falconwing"
PROVIDES = "virtual/bootsector"
RPROVIDES_${PN} = "virtual/bootsector"
PR = "r11"
COMPATIBLE_MACHINE = "chumby-falconwing"


SRC_URI = "file://chumby-bootshell-1.0.tar.gz"

S = "${WORKDIR}/src"

do_compile () {
    oe_runmake 'LINUX_DIR=${STAGING_KERNEL_DIR}'
}

FILES_${PN} = "/boot"

addtask deploy before do_build after do_compile

do_deploy () {
    install -d ${DEPLOY_DIR_IMAGE}
    install -m 0644 output/chumby_boot.rom ${DEPLOY_DIR_IMAGE}/chumby_shell.bin
#    package_stagefile_shell ${DEPLOY_DIR_IMAGE}/chumby_shell.bin
}
