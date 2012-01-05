Building Custom Firmware for the Chumby Hackers Board
=====================================================

Chumby has made an OpenEmbedded overlay available for building firmware for
their products. Unfortunately the distributed source package has several issues
which this package corrects, including:

* simplifying the installation process
* correcting the checksum for Linux kernel source file
* correcting the source directory for linux kernel source file
* restoring files missing from the Linux kernel distribution

Getting Started
===============

Create a clean Debian system as follows:

* Download the ISO file for the netinst from http://www.debian.org/CD/netinst/.
* Select only the minimum required packages, plus the SSH server.
* Follow the installer prompts to complete the installation and reboot.

Once you have a Debian console, perform the following:

1. Install git-core and make manually, and change the default shell from dash to bash (OpenEmbedded prefers bash).

    ```
    su -
    apt-get install git-core make
    dpkg-reconfigure dash   # Set to <no> when prompted
    exit
   ```

1. Download the chumby-oe git project

    ```
    cd ~
    git clone https://github.com/clearwater/chumby-oe.git 
    ```

1. Use the setup make target install required Debian packages and the git repositories for bitbake and openembedded. You will be prompted for the root password to install the packages. On a clean system this installs over 200 packages.  It takes about an hour on my test box.

    ```
    cd ~/chumby-oe
    make setup
    ```

1. Build the firmware.  This will pull source code from the net (about 1.3GB in total) to build the image, including a lot of stuff that seems unnecessary.  It takes a long time; about 24 hours on my build machine.

    ```
    cd ~/chumby-oe
    make
    ```

Issues
======

This build depends on a whole lot of external resources.  Periodically the source files named in the recipes disappear or change, and you will get build errors.  Typically this can be solved in one of these ways:

* Locate a copy of the original distribution file and save it to ```~/chumby-oe/sources```, 
* Update the failing recipe with new checksums.  Use ``md5 <filename>`` and ``shasum -a 256 <filename>`` to compute new checksums.
* Update the recipe that depends on the failing recipe to use a later version for which source is available.  This may require writing new recipe files for the new version.  Obviously you risk breakage if the new version is incompatible in some way.

Resources
=========

1.  [Chumby Hacker Boards](http://wiki.chumby.com/index.php/Chumby_hacker_board_beta)
2.  [Buy Chumby Hacker Boards at Adafruit](http://www.adafruit.com/category/46)
3.  [OpenEmbedded Wiki](http://www.openembedded.org/index.php/Main_Page)
4.  [Bitbake Manual](http://bitbake.berlios.de/manual/)
5.  [Chumby Hackers Board Forum](http://forum.chumby.com/viewforum.php?id=20)
6.  [Chumby Building OpenEmbedded](http://wiki.chumby.com/index.php/Building_OpenEmbedded_%28Beta%29)
7.  [Chumby Quickstarting OE](http://wiki.chumby.com/index.php/Quickstarting_OE)
