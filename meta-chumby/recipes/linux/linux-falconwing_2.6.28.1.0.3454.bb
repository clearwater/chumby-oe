require recipes/linux/linux.inc

PR = "r0"

COMPATIBLE_MACHINE = "chumby-falconwing"

PROVIDES = "virtual/kernel"

SRC_URI = "http://files.chumby.com/source/falconwing/build3454/linux-2.6.28.mx233-falconwing-1.0.7.tgz \
           file://defconfig \
"
S = "${WORKDIR}/linux-2.6.28.mx233-falconwing-1.0.7"

# Mark archs/machines that this kernel supports
DEFAULT_PREFERENCE = "-1"
DEFAULT_PREFERENCE_chumby-falconwing = "1"

SRC_URI[md5sum] = "7ff9f40b4c08e7393098fe231197e0f0"
SRC_URI[sha256sum] = "b6df540b1e450b791c659481d54e4187d554aef5fff12a004dda76ce92bd6413"
