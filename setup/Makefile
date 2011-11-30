# Add a line like this to the end of /etc/sudoers
# guy ALL = NOPASSWD: /usr/bin/aptitude

PACKAGES = \
	sudo \
	emacs \
	sed \
	wget \
	cvs \
	subversion \
	git-core \
	coreutils \
	unzip \
	texi2html \
	texinfo \
	docbook-utils \
	gawk \
	python-pysqlite2 \
	diffstat \
	help2man \
	make \
	gcc \
	build-essential \
	g++ \
	desktop-file-utils \
	chrpath \
	libxml2-utils \
	xmlto \
	screen \
	python-ply \
	xsltproc \
	python-progressbar \
	python-psyco

.PHONY:$(PACKAGES) packages submodules

# Install packages all at once
all: packages submodules patches

submodules:
	(cd ..; git submodule init)
	(cd ..; git submodule update)

packages:
	sudo aptitude install $(PACKAGES)

patches:
	(cd ../openembedded/recipes/fakeroot; patch --forward -p1) < fakeroot.patch
	patch --forward ../openembedded/recipes/binutils/binutils_2.20.1.bb binutils.patch

