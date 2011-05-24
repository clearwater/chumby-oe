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
BITBAKE_TGZ:=bitbake-$(BITBAKE_VERSION).tar.gz

.PHONY:$(PACKAGES) chumby openembedded

# Install packages all at once
all:
	sudo aptitude install $(PACKAGES)

# Install packages one at a time
each_package: $(PACKAGES)

# Rule used to install each package
$(PACKAGES):
	sudo aptitude install $@

openembedded:
	git clone git://git.openembedded.org/openembedded ../openembedded

chumby:
	$(DOWNLOAD) http://files.chumby.com/hacks/$(CHUMBY_TGZ)
	tar -C .. -zxvf $(DOWNLOAD_DIR)/$(CHUMBY_TGZ)

bitbake:
	$(DOWNLOAD) http://download.berlios.de/bitbake/$(BITBAKE_TGZ)
	tar -zxvf $(DOWNLOAD_DIR)/$(BITBAKE_TGZ)
	@echo "su and run the following:"
	@echo "  cd bitbake-$(BITBAKE_VERSION)"
	@echo "  python setup.py install"
