DESCRIPTION = "Chumby register reader and writer"
HOMEPAGE = "http://www.chumby.com/"
AUTHOR = "Sean Cross"
LICENSE = "BSD"
PR = "r0"
PN = "regutil-${CNPLATFORM}"
RPROVIDES = "regutil"

SRC_URI = "http://files.chumby.com/source/falconwing/build2370/utils-1.0.tgz"
S = "${WORKDIR}/utils-1.0"

do_compile() {
    ${CC} ${CFLAGS} ${LDFLAGS} regutil.c -o regutil -DCNPLATFORM_${CNPLATFORM}
}

do_install() {
    install -d ${D}/${sbindir}
    install -m 0755 regutil ${D}/${sbindir}
}

SRC_URI[md5sum] = "72e935ae4eab40ffb50d9381578082eb"
SRC_URI[sha256sum] = "b231ab940c2f50fb7fef6b8bee8032b65eaa67f4b4bc7afd206455224721460f"
