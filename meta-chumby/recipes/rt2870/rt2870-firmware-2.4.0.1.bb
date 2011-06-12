DESCRIPTION = "Firmware for rt2870 based USB wifi adaptors"
LICENSE = "unknown"

PR = "r1"

SRC_URI = "file://firmware/2010_0709_RT2870_Linux_STA_v2.4.0.1.tar.bz2"

S = "${WORKDIR}/2010_0709_RT2870_Linux_STA_v${PV}/common/rt2870.bin"

do_install() {
	install -d ${D}/${base_libdir}/firmware
	install -m 0644 rt2870.bin ${D}/${base_libdir}/firmware/
}

FILES_${PN} = "${base_libdir}/firmware/"
PACKAGE_ARCH = "all"
