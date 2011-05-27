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

CHUMBY_TGZ:=meta-chumby-1.2.tar.gz
DOWNLOAD_DIR:=downloads
DOWNLOAD:=wget --directory-prefix $(DOWNLOAD_DIR) --continue
BITBAKE_VERSION:=1.12.0
BITBAKE_PATH:=bitbake-$(BITBAKE_VERSION)
BITBAKE_TGZ:=bitbake-$(BITBAKE_VERSION).tar.gz

.PHONY:$(PACKAGES) packages openembedded submodules

# Install packages all at once
all: packages openembedded submodules

submodules:
	git submodule init
	git submodule update

packages:
	sudo aptitude install $(PACKAGES)

# Rule used to install each package
$(PACKAGES):
	sudo aptitude install $@

openembedded:
	git clone git://git.openembedded.org/openembedded ../openembedded
