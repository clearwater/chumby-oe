require recipes/linux/linux.inc

PR = "r0"

COMPATIBLE_MACHINE = "chumby-silvermoon"

SRC_URI = "http://files.chumby.com/source/silvermoon/linux-2.6.28.silvermoon-3577.tar.gz \
           file://defconfig \
"
S = "${WORKDIR}/src"

# Mark archs/machines that this kernel supports
DEFAULT_PREFERENCE = "-1"
DEFAULT_PREFERENCE_chumby-silvermoon = "1"

SRC_URI[md5sum] = "5eaa77566a5a712b0f392caae509635f"
SRC_URI[sha256sum] = "173fd738a9f5e52bf559b5fe28a5aaaf1ae02643bb7c83678d66229c48ac9abc"
