DESCRIPTION = "Chumby boot blobs - miscellaneous binary blobs"
HOMEPAGE = "http://www.chumby.com/"
AUTHOR = "Sean Cross"
LICENSE = "BSD"

PROVIDES = "chumby-blobs"
COMPATIBLE_MACHINE = "chumby-falconwing"

SRC_URI = "http://files.chumby.com/source/falconwing/build2370/bootstream-1.0.tgz"
S = "${WORKDIR}/bootstream-1.0/images"

do_compile() {
    true
}

# chumby_stub.bin is a ROM, with no gnu_hash.
INSANE_SKIP_${PN} = True

FILES_${PN} = "/boot"

do_install() {
    install -d ${D}/boot
    install -m 0755 ${S}/power ${D}/boot/power
    install -m 0755 ${S}/clocks ${D}/boot/clocks
    install -m 0755 ${S}/sdram_prep ${D}/boot/sdram_prep
    install -m 0755 ${S}/4_1.bin ${D}/boot/4_1.bin
    install -m 0755 ${S}/4_2.bin ${D}/boot/4_2.bin
    install -m 0755 ${S}/5_1.bin ${D}/boot/5_1.bin
    install -m 0755 ${S}/5_2.bin ${D}/boot/5_2.bin
    install -m 0755 ${S}/5_3.bin ${D}/boot/5_3.bin
}

do_runstrip() {
    true
}

# Copy the resulting file to the image directory
do_deploy() {
    install -d ${DEPLOY_DIR_IMAGE}
    install ${S}/power ${DEPLOY_DIR_IMAGE}/power
    install ${S}/clocks ${DEPLOY_DIR_IMAGE}/clocks
    install ${S}/sdram_prep ${DEPLOY_DIR_IMAGE}/sdram_prep
    install ${S}/4_1.bin ${DEPLOY_DIR_IMAGE}/4_1.bin
    install ${S}/4_2.bin ${DEPLOY_DIR_IMAGE}/4_2.bin
    install ${S}/5_1.bin ${DEPLOY_DIR_IMAGE}/5_1.bin
    install ${S}/5_2.bin ${DEPLOY_DIR_IMAGE}/5_2.bin
    install ${S}/5_3.bin ${DEPLOY_DIR_IMAGE}/5_3.bin
#    package_stagefile_shell ${DEPLOY_DIR_IMAGE}/power
#    package_stagefile_shell ${DEPLOY_DIR_IMAGE}/clocks
#    package_stagefile_shell ${DEPLOY_DIR_IMAGE}/sdram_prep
#    package_stagefile_shell ${DEPLOY_DIR_IMAGE}/4_1.bin
#    package_stagefile_shell ${DEPLOY_DIR_IMAGE}/4_2.bin
#    package_stagefile_shell ${DEPLOY_DIR_IMAGE}/5_1.bin
#    package_stagefile_shell ${DEPLOY_DIR_IMAGE}/5_2.bin
#    package_stagefile_shell ${DEPLOY_DIR_IMAGE}/5_3.bin
}
do_deploy[dirs] = "${S}"
addtask deploy before do_build after do_compile

SRC_URI[md5sum] = "88941edb11ad1649336f260071d58bf4"
SRC_URI[sha256sum] = "c84a395e24280860af756c5532bff7d4f1c7ebe97c8aaa4868c0a12ffd19c8ab"
