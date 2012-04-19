DESCRIPTION = "ralink-rt2870 usb driver"
LICENSE = "unknown"

# This is an empty package that aggregates the dependencies
# for the ralink rt2870 USB wireless module.

RDEPENDS = " \
	 compat-wireless-rt2x00 \
	 rt2870-firmware \
"
	 
# NOTE: This package is simply an aggregator for a list of other dependencies
ALLOW_EMPTY = "1"
