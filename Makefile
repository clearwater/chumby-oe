# On debian: 
# sudo dpkg-reconfigure dash

# Upon completion the bootable rom image
# will be at:
# $(OUTPUT)/deploy/eglibc/images/$(MACHINE)/rom-$(MACHINE)-$(CHUMBY-IMAGE).img
# output-angstrom-.9/deploy/eglibc/images/chumby-falconwing/rom-chumby-falconwing-chumby-starter-image.img

export CHUMBY_HOME:=$(shell pwd)
# You can set CHUMBY_HOME explicity like this:
# export CHUMBY_HOME:=/home/guy/chumby-oe
export CHUMBY_IMAGE:=chumby-dev-image
export PATH:=${CHUMBY_HOME}/bitbake/bin:${PATH}
export MACHINE:=chumby-falconwing
export BBPATH:=${CHUMBY_HOME}/meta-chumby:${CHUMBY_HOME}/openembedded
export DISTRO:=angstrom-2010.x
export BB_ENV_EXTRAWHITE:=MACHINE DISTRO TOPDIR
OUTPUT=output-angstrom-.9
# You can build any subcomponent defined in a bb file from the command line with:
# make unit UNIT=binutils_2.20.1
UNIT=linux-falconwing_2.6.28.1.0.3454

.PHONY:	build test unit clean setup

build:
	bitbake $(CHUMBY_IMAGE) 2>&1 | tee build.log

clean:
	bitbake $(CHUMBY_IMAGE) --cmd=clean 2>&1 | tee build.log

test:
	bitbake --dry-run $(CHUMBY_IMAGE)

unit:
	bitbake --force --buildfile=$(UNIT) 2>&1 | tee unit.log

unitdep:
	bitbake -g $(UNIT)

unitclean:
	bitbake --buildfile=$(UNIT) --cmd=clean 2>&1 | tee unit.log

reallyclean:
	rm -rf $(OUTPUT)

setup:
	make -C setup
