#Angstrom bootstrap image
# Derived from chumby-starter-image,
# using info gleaned from the example posted by Madox here:
# http://forum.chumby.com/viewtopic.php?pid=37346#p37346

# Some notes on recipe syntax:
#
# -native suffix on packages means they will be compiled for the build host.
# 
# http://docs.openembedded.org/usermanual/html/commonuse_new_image.html
# IMAGE_BASENAME to give a name for your own image
# PACKAGE_INSTALL to give a list of packages to install into the image
# RDEPENDS to give a list of recipes which are needed to be built to create this image
# IMAGE_LINGUAS is an optional list of languages which has to be installed into the image
#
# task-base-extended is task-base plus wifi and bluetooth
#
# compat-wireless-2.6-old doesn't look old - 
# looks like the current 2011-12 version to me?
#
# kernel-module-led-class 
# required for rt2x00 it seems to avoid the following errors
# trying to load the rt2x00 wifi drivers.
#
#   [  115.190000] usb 1-1.1: new high speed USB device using fsl-ehci and address 5
#   [  115.330000] usb 1-1.1: configuration #1 chosen from 1 choice
#   [  115.490000] compat: Unknown symbol led_classdev_unregister
#   [  115.540000] compat: Unknown symbol led_classdev_unregister
#----------------------------------------------------------------------

IMAGE_PREPROCESS_COMMAND = "create_etc_timestamp"

ANGSTROM_EXTRA_INSTALL ?= ""

# set a value for ssh daemon if not already defined
#
DISTRO_SSH_DAEMON ?= "dropbear"

ZZAPSPLASH = ' ${@base_contains("MACHINE_FEATURES", "screen", "psplash-zap", "",d)}'

DEPENDS = "task-base-extended \
           ${SPLASH} \
           ${ZZAPSPLASH} \
	   config-util \
	   regutil-${CNPLATFORM} \
	   "

IMAGE_INSTALL = "task-base \
	    task-base-wifi \
	    ${ANGSTROM_EXTRA_INSTALL} \
	    ${SPLASH} \
	    ${ZZAPSPLASH} \
	    config-util \
	    kernel-module-led-class \
	    compat-wireless-rt2x00 \
	    mpd \
	    bash \
	    ${DISTRO_SSH_DAEMON} \
	    regutil-${CNPLATFORM} \
	    "

IMAGE_LINGUAS = ""

inherit image
