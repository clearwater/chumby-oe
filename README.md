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

Here's the process from the beginning:

 - You will need a github login.  If you don't have one already, sign up for one now.
 - Download the ISO file for the netinst from [http://www.debian.org/CD/netinst/].  I used bittorrent.
 - Create a new VirtualBox image under OS X and boot from the Debian ISO.  The default disk size (8Gb) is NOT big enough.  I used 40Gb.  I named my image "debian" and VirtualBox recognized it as a Debian install - neat trick.
 - Select only the minimum required packages, plus the SSH server.
 - Follow the installer prompts to complete the installation and reboot.
 - Set the network to "bridged" mode so you can ssh into the virtual box over your network.

Once you have a Debian console, login *as root* and perform the following steps:

Install sudo and git-core
    aptitude install sudoers git-core

Give yourself access to aptitude through sudo, and turn off dash (OE bitches if you leave it on.)

    echo "guy ALL = NOPASSWD: /usr/bin/aptitude" >> /etc/sudoers
    dpkg-reconfigure dash

Now login to your user account and perform the following:

0) Create a ssh key for github access, and add it to your github account's list of authorized keys.
 
    ssh-keygen -t rsa -C "your_email@youremail.com"
    cat ~/.ssh/id_rsa.pub

1) Download the bootstrap tools:

    git clone git@github.com:clearwater/chumby-oe.git

2) Use the bootstrap tools to download the OE and chumby tools.  This triggers the installation of the required packages (almost 200 of them), plus the downloading of the OpenEmbedded toolchain from github (big!) and some other downloads.  It will take a while.

    cd chumby-oe/bootstrap
    make all

3) Install the correct version of bitbake (as root)

    cd ~/chumby-oe/bootstrap/bitbake-1.12.0 
    su
    python setup.py install
    exit

4) Use bitbake to download and build the OE toolchain

    cd ~/chumby-oe
    source setup.sh
    make


Checksum Errors
----

I found it necessary to update the checksums in 
  ./meta-chumby/recipes/linux/linux-falconwing_2.6.28.1.0.3454.bb
as follows:
  SRC_URI[md5sum] = "e4769e2f866d1f105cd7ad8f9f196aad"
  SRC_URI[sha256sum] = "11ad31a3d2487b48d9ff95df7494903d0fe109e9880a9569146f236703100a3a"



	

