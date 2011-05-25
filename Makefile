# On debian: 
# sudo dpkg-reconfigure dash

export CHUMBY_HOME:=$(shell pwd)
# You can set CHUMBY_HOME explicity like this:
# export CHUMBY_HOME:=/home/guy/chumby-oe
export CHUMBY_IMAGE:=chumby-starter-image
export PATH:=${CHUMBY_HOME}/bitbake/bin:${PATH}
export MACHINE:=chumby-falconwing
export BBPATH:=${CHUMBY_HOME}/meta-chumby:${CHUMBY_HOME}/openembedded
export DISTRO:=angstrom-2010.x
export BB_ENV_EXTRAWHITE:=MACHINE DISTRO TOPDIR
OUTPUT=output-angstrom-.9

.PHONY:	build buildlog test clean bootstrap

build:
	bitbake $(CHUMBY_IMAGE)

buildlog:
	bitbake $(CHUMBY_IMAGE) 2>&1 > build.log

test:
	bitbake --dry-run $(CHUMBY_IMAGE)

unit:
	bitbake -b linux-falconwing_2.6.28.1.0.3454.bb 2>&1 > unit.log

clean:
	rm -rf $(OUTPUT)

bootstrap:
	make -C bootstrap
