require recipes/linux/linux.inc

PR = "r0"

COMPATIBLE_MACHINE = "chumby-falconwing"

PROVIDES = "virtual/kernel"

SRC_URI = "git://github.com/clearwater/linux-2.6-imx.git;tag=falconwing-1.0.7;protocol=git \
           file://defconfig \
"

S = "${WORKDIR}/git"

# Mark archs/machines that this kernel supports
DEFAULT_PREFERENCE = "-1"
DEFAULT_PREFERENCE_chumby-falconwing = "1"

# NOTE - these checksums were modified from the original distribution.
# See:    https://github.com/clearwater/chumby-oe
#
SRC_URI[md5sum] = "e4769e2f866d1f105cd7ad8f9f196aad"
SRC_URI[sha256sum] = "11ad31a3d2487b48d9ff95df7494903d0fe109e9880a9569146f236703100a3a"
