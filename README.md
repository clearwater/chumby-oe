Building a System Image for a Chumby Hackers Board
====

Chumby have very kindly made available a kit to build
a custom bootable image for the Chumby Hackers board using
OpenEmbedded.  

http://wiki.chumby.com/index.php/Building_OpenEmbedded_%28Beta%29

The OpenEmbedded tool does not run easily (at all?) under
FreeBSD or BSD-derived OS X.  I'm very familiar with FreeBSD,
and after spending some time trying to resolve all of the issues with OE
under FreeBSD I decided that developing for embedded systems
are hard enough without introducing a new variable - a toolchain
of unknown quality.  I decided to switch tacks and use Debian running in a virtual machine 
using VirtualBox on Mac OSX.  That should let me use OE the way
it was intended.

Here's the process for installing Debian and getting OE running, from the beginning:

 - You will need a github login.  If you don't have one already, sign up for one now.
 - Download the ISO file for the netinst from http://www.debian.org/CD/netinst/.  I used bittorrent.
 - Create a new VirtualBox image under OS X and boot from the Debian ISO.  The default disk size (8Gb) is NOT big enough.  I used 40Gb.  I named my image "debian" and VirtualBox recognized it as a Debian install - neat trick.
 - Select only the minimum required packages, plus the SSH server.
 - Follow the installer prompts to complete the installation and reboot.
 - Set the network to "bridged" mode so you can ssh into the virtual box over your network.

Once you have a Debian console, login *as root* and perform the following steps:

1)  Install sudo and git-core
    aptitude install sudoers git-core

2)  Give yourself access to aptitude through sudo, and turn off dash (OE bitches if you leave it on.)

    echo "guy ALL = NOPASSWD: /usr/bin/aptitude" >> /etc/sudoers
    dpkg-reconfigure dash

Now login to your user account and perform the following:

3)  Create a ssh key for github access, and add it to your github account's list of authorized keys.
 
    ssh-keygen -t rsa -C "your_email@youremail.com"
    cat ~/.ssh/id_rsa.pub

Follow the instructions on github for adding this key to the list of keys in your github account.  You can confirm that your key is correctly installed as follows:

    > ssh git@github.com
    Hi guyc! You've successfully authenticated, but GitHub does not provide shell access.
    Connection to github.com closed.

If your key has not been correctly installed you will see an error message instead.  See http://help.github.com/troubleshooting-ssh/ for details.

4) Download the bootstrap tools:

   cd ~
   git clone git@github.com:clearwater/chumby-oe.git

5) Use the bootstrap tools to install required packages, download OpenEmbedded, bitbake, and the chumby prproject files.  This triggers the installation of the required packages (almost 200 of them), plus the downloading of the OpenEmbedded toolchain from github (big!) and some other downloads.  It will take a while.

    cd ~/chumby-oe
    make bootstrap

The makefile incorporates a patch to the chumby recipe file to correct the checksums in this file:
  ./meta-chumby/recipes/linux/linux-falconwing_2.6.28.1.0.3454.bb
the correct checksums are:
  SRC_URI[md5sum] = "e4769e2f866d1f105cd7ad8f9f196aad"
  SRC_URI[sha256sum] = "11ad31a3d2487b48d9ff95df7494903d0fe109e9880a9569146f236703100a3a"

4) Use bitbake to download and build the cross-compiler toolchain and OS

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




	

