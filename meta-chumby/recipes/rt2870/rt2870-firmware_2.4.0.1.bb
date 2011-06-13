DESCRIPTION = "Firmware for rt2870 based USB wifi adaptors"
LICENSE = "closed"

SRC_URI = "file://2010_0709_RT2870_Linux_STA_v2.4.0.1.tar.bz2 \
	   file://50-compat_firmware.rules" 

# defaults to 2010_0709_RT2870_Linux_STA_${PV}" (no 'v')
S = "${WORKDIR}/2010_0709_RT2870_Linux_STA_v${PV}"

do_install() {
	pwd
	install -d ${D}/${base_libdir}/firmware
	install -m 0644 common/rt2870.bin ${D}${base_libdir}/firmware/

	install -d ${D}${sysconfdir}/udev/rules.d/
	install -m 0755 ${WORKDIR}/*.rules ${D}${sysconfdir}/udev/rules.d/
}

# Do NOT try to compile the contents in the bz2 file.
do_compile() {
}

FILES_${PN} = "${base_libdir}/firmware/"
PACKAGE_ARCH = "all"
