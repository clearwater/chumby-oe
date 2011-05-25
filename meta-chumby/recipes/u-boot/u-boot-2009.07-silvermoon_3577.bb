require u-boot.inc

PR = "r0"

PROVIDES = "virtual/bootloader"
RPROVIDES_${PN} = "virtual/bootloader"
COMPATIBLE_MACHINE = "chumby-silvermoon"

SRC_URI = "http://files.chumby.com/source/silvermoon/u-boot-2009.07-silvermoon-3577.tar.gz"
S = "${WORKDIR}/src"

DEFAULT_PREFERENCE = "-1"
DEFAULT_PREFERENCE_chumby-silvermoon = "1"

SRC_URI[md5sum] = "add6df2421d31f16a0a187e554224412"
SRC_URI[sha256sum] = "f222b4edf293d5de6c0802f3669b697e20478a6122662b0a15a566fcd995dddc"
