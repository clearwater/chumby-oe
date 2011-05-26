Building a Custom Firmware for the Chumby Hackers Board
==================================================

Chumby has made an OpenEmbedded overlay available for building firmware for
their products. Unfortunately the distributed source package has several issues
which this package corrects, including:

* simplifying the installation process
* correcting the checksum for linux kernel source file
* correcting the source directory for linux kernel source file

Getting Started
===============

* Download the ISO file for the netinst from http://www.debian.org/CD/netinst/.
* Select only the minimum required packages, plus the SSH server.
* Follow the installer prompts to complete the installation and reboot.
* Set the network to "bridged" mode so you can ssh into the virtual box over your network.

Once you have a Debian console, perform the following:

1. Install git-core, to download this repository.

    `sudo apt-get install git-core`

1. Disable dash, as OpenEmbedded prefers Bash.

    sudo dpkg-reconfigure dash

1. Download the bootstrap tools

    ```bash
    cd ~
    git clone git@github.com:clearwater/chumby-oe.git
    ```

1. Use the bootstrap tools to install required packages. This triggers the installation of over 200 packages, it will take a while.

    cd ~/chumby-oe
    make bootstrap

1. Create the firmware

    cd ~/chumby-oe
    make

Resources
=========

1.  [Chumby Hacker Boards](http://wiki.chumby.com/index.php/Chumby_hacker_board_beta)
2.  [Buy Chumby Hacker Boards at Adafruit](http://www.adafruit.com/category/46)
3.  [OpenEmbedded Wiki](http://www.openembedded.org/index.php/Main_Page)
4.  [Bitbake Manual](http://bitbake.berlios.de/manual/)
5.  [Chumby Hackers Board Forum](http://forum.chumby.com/viewforum.php?id=20)
6.  [Chumby Building OpenEmbedded](http://wiki.chumby.com/index.php/Building_OpenEmbedded_%28Beta%29)
7.  [Chumby Quickstarting OE](http://wiki.chumby.com/index.php/Quickstarting_OE)
