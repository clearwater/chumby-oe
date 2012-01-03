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
    git clone https://guyc@github.com/clearwater/chumby-oe.git 
    ```

1. Use the setup make target install required packages and other repositories. You will be prompted for the root password to invoke aptitude. This triggers the installation of over 200 packages and takes about an hour on my system.

    ```
    cd ~/chumby-oe
    make setup
    ```

1. Build the firmware.  This will pull a bunch of source packages from the network and build them.  It takes a long time; about 24 hours on my build machine.

    ```
    cd ~/chumby-oe
    make
    ```

Resources
=========

1.  [Chumby Hacker Boards](http://wiki.chumby.com/index.php/Chumby_hacker_board_beta)
2.  [Buy Chumby Hacker Boards at Adafruit](http://www.adafruit.com/category/46)
3.  [OpenEmbedded Wiki](http://www.openembedded.org/index.php/Main_Page)
4.  [Bitbake Manual](http://bitbake.berlios.de/manual/)
5.  [Chumby Hackers Board Forum](http://forum.chumby.com/viewforum.php?id=20)
6.  [Chumby Building OpenEmbedded](http://wiki.chumby.com/index.php/Building_OpenEmbedded_%28Beta%29)
7.  [Chumby Quickstarting OE](http://wiki.chumby.com/index.php/Quickstarting_OE)
