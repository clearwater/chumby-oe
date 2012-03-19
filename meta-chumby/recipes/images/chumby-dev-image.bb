#Angstrom bootstrap image

IMAGE_PREPROCESS_COMMAND = "create_etc_timestamp"

ANGSTROM_EXTRA_INSTALL ?= ""

ZZAPSPLASH = ' ${@base_contains("MACHINE_FEATURES", "screen", "psplash-zap", "",d)}'

DEPENDS = "task-base \
           ${SPLASH} \
           ${ZZAPSPLASH} \
config-util regutil-${CNPLATFORM} \
	   "

IMAGE_INSTALL = "task-base \
	    ${ANGSTROM_EXTRA_INSTALL} \
	    ${SPLASH} \
	    ${ZZAPSPLASH} \
	    config-util regutil-${CNPLATFORM} \
	    task-native-sdk \
	    "

IMAGE_LINGUAS = ""

inherit image
