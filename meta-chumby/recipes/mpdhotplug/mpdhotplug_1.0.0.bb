DESCRIPTION = "Hotplug client for Music Player Daemon (mpd)"
LICENSE = "GPLv2"

inherit autotools

DEPENDS = "\
    libmpd

SRC_URI = "git://github.com/clearwater/mpd-hotplug.git;tag=HEAD;protocol=git"

S = "${WORKDIR}/git"

