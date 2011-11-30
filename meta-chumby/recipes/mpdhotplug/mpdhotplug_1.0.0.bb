DESCRIPTION = "Hotplug client for Music Player Daemon (mpd)"
LICENSE = "GPLv2"

inherit autotools

# REVISIT - use a git release tag here once we have a working release

SRC_URI = "git://github.com/clearwater/mpd-hotplug.git;tag=HEAD;protocol=git"

S = "${WORKDIR}/git"
