inherit native

DESCRIPTION = "Chumby config block manager"
HOMEPAGE = "http://www.chumby.com/"
AUTHOR = "Henry Groover"
LICENSE = "GPLv2"
PR = "r0"

SRC_URI = "http://files.chumby.com/source/falconwing/build2370/utils-1.0.tgz"
S = "${WORKDIR}/utils-1.0"

do_compile() {
    ${CXX} ${CFLAGS} ${LDFLAGS} config_util.cpp -o config_util
}

# Empty stage_native.
# native.bbclass will try to call this and run oe_runmake.  That doesn't work
# for our packages, so just make it a NO-OP.
# Only called for -native flavors.
do_stage_native() {
    true
}

# Poky requires this, otherwise it flat-out fails to build
sstate_task_postfunc() {
    true
}

do_install() {
    install -d ${bindir}
    install -m 0755 ${S}/config_util ${bindir}/config_util
}

SRC_URI[md5sum] = "72e935ae4eab40ffb50d9381578082eb"
SRC_URI[sha256sum] = "b231ab940c2f50fb7fef6b8bee8032b65eaa67f4b4bc7afd206455224721460f"
