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
# task-base-wifi is task-base-extended without bluetooth
#
# DEPENDS - Specifies build-time dependencies, via a list of bitbake recipes 
# to build prior to build the recipe. These are programs (flex-native) or
#  libraries (libpcre) that are required in order to build the package.
#
# RDEPENDS - specifies run-time dependencies, via a list of packages to
# install prior to installing the current package. These are programs or
# libraries that are required in order to run the program. Note that 
# libraries which are dynamically linked to an application will be 
# automatically detected and added to RDEPENDS and therefore do not need 
# to be explicitly declared. If a library was dynamically loaded then 
# it would need to be explicitly listed.
#
# IMAGE_INSTALL - Specifies other packages to be installed along with the current one.
#----------------------------------------------------------------------

IMAGE_PREPROCESS_COMMAND = "create_etc_timestamp"

ANGSTROM_EXTRA_INSTALL ?= ""

# set a value for ssh daemon if not already defined
#
DISTRO_SSH_DAEMON ?= "dropbear"

ZZAPSPLASH = ' ${@base_contains("MACHINE_FEATURES", "screen", "psplash-zap", "",d)}'

DEPENDS = "\
    task-base \
    task-base-wifi \
    ${SPLASH} \
    ${ZZAPSPLASH} \
    config-util \
    regutil-${CNPLATFORM} \
    "

IMAGE_INSTALL = "\
    task-base \
    task-base-wifi \
    ${ANGSTROM_EXTRA_INSTALL} \
    ${SPLASH} \
    ${ZZAPSPLASH} \
    config-util \
    rt2870 \
    connman \
    mpd \
    mpdhotplug \
    ${DISTRO_SSH_DAEMON} \
    regutil-${CNPLATFORM} \
"

IMAGE_LINGUAS = ""

inherit image
