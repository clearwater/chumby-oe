inherit module

DESCRIPTION = "Driver for sd8686 wifi chipset found in Silvermoon"
HOMEPAGE = "http://www.chumby.com"
SECTION = "kernel/modules"
LICENSE = "GPL"
PR = "r0"

SRC_URI = "http://files.chumby.com/source/silvermoon/silvermoon-1.0.1/wifi-1.0.tgz"
S = "${WORKDIR}/wifi-1.0/sd8686/wlan_src/"

EXTRA_OEMAKE = "KERNDIR=${STAGING_KERNEL_DIR}"

PACKAGES += "sd8686-firmware"

do_install() {
        install -d ${D}${base_libdir}/modules/${KERNEL_VERSION}/extra
        install -m 0644 sd8xxx${KERNEL_OBJECT_SUFFIX} ${D}${base_libdir}/modules/${KERNEL_VERSION}/extra/
        install -d ${D}/lib/firmware/mrvl
	install -m 0644 ../FwImage/sd8686.bin ${D}/lib/firmware/mrvl
	install -m 0644 ../FwImage/helper_sd.bin ${D}/lib/firmware/mrvl
}

FILES_${PN}-firmware = "/lib/firmware"
PACKAGES =+ "${PN}-firmware"
RDEPENDS_${PN} = "${PN}-firmware"

SRC_URI[md5sum] = "718bf749a52b9f5ca8a9035bfccb8f8a"
SRC_URI[sha256sum] = "3f5aa262f43e2c5d333abccaa59279bd45e1b447f251e7a06dce012e608b9f7d"
