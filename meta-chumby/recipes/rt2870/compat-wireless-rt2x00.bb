include compat-wireless.inc

do_configure() {
	cd ${S}
        ./scripts/driver-select rt2x00
}
