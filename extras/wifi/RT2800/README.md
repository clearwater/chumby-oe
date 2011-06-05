RT2800 Wifi Drivers
===================

(This is still very raw - not yet integrated into the build.)

This is the wireless driver update described at http://wiki.ladyada.net/chumbyhackerboard/wifi
and distributed as http://wiki.ladyada.net/_media/chumbyhackerboard/rt2800-update.zip

The zip file contents are:

    Archive:  rt2800-update.zip
     Length   Method    Size  Cmpr    Date    Time   CRC-32   Name
    --------  ------  ------- ---- ---------- ----- --------  ----
      670747  Defl:N   669715   0% 02-24-2011 11:39 64663a8a  update-modules.sh
    --------          -------  ---                            -------
      670747           669715   0%                            1 file

The script file contains a tar file along with this installation code:

    #!/bin/sh
    fail() {
        echo "Unable to update: $*"
        exit 1
    }
    
    NAME=$(readlink -f $0)
    
    mount -oremount,rw / || fail "Cannot remount / as rw"
    rm -rf /mnt/storage/old-updates
    mv /lib/modules/2.6.28-chumby/updates /mnt/storage/old-updates || fail "Cannot remove previous compat-wireless"
    (cd /lib/modules/2.6.28-chumby && dd if=${NAME} skip=2 | tar xz) || fail "Cannot extract new compat-wireless"
    depmod -a
    echo 'SUBSYSTEM=="compat_firmware", ACTION=="add", RUN+="firmware.sh"' >> /lib/udev/rules.d/50-firmware.rules
    mount -oremount,ro / || fail "Cannot remount / as ro"
    sync
    echo "compat-wireless updated.  Reboot to use it."
    exit 0

To extract the tar file which is embedded in the sh file:

    dd if=update-modules.sh skip=2 > rt2800-update.tar.gz

The tar file contains these files:

    drwxr-xr-x  0 root   root        0 Feb  7 13:58 updates/
    drwxr-xr-x  0 root   root        0 Feb  7 13:58 updates/drivers/
    drwxr-xr-x  0 root   root        0 Feb  7 13:58 updates/drivers/bluetooth/
    -rw-r--r--  0 root   root    26859 Feb  7 13:58 updates/drivers/bluetooth/hci_uart.ko
    -rw-r--r--  0 root   root    20172 Feb  7 13:58 updates/drivers/bluetooth/btusb.ko
    -rw-r--r--  0 root   root     8683 Feb  7 13:58 updates/drivers/bluetooth/btsdio.ko
    -rw-r--r--  0 root   root     7829 Feb  7 13:58 updates/drivers/bluetooth/bcm203x.ko
    -rw-r--r--  0 root   root     9451 Feb  7 13:58 updates/drivers/bluetooth/bpa10x.ko
    -rw-r--r--  0 root   root     5878 Feb  7 13:58 updates/drivers/bluetooth/ath3k.ko
    -rw-r--r--  0 root   root     8929 Feb  7 13:58 updates/drivers/bluetooth/hci_vhci.ko
    -rw-r--r--  0 root   root    16554 Feb  7 13:58 updates/drivers/bluetooth/btmrvl_sdio.ko
    -rw-r--r--  0 root   root    21897 Feb  7 13:58 updates/drivers/bluetooth/btmrvl.ko
    -rw-r--r--  0 root   root    13896 Feb  7 13:58 updates/drivers/bluetooth/bfusb.ko
    drwxr-xr-x  0 root   root        0 Feb  7 13:58 updates/drivers/ssb/
    -rw-r--r--  0 root   root    35659 Feb  7 13:58 updates/drivers/ssb/ssb.ko
    drwxr-xr-x  0 root   root        0 Feb  7 13:58 updates/drivers/net/
    drwxr-xr-x  0 root   root        0 Feb  7 13:58 updates/drivers/net/wireless/
    -rw-r--r--  0 root   root    28952 Feb  7 13:58 updates/drivers/net/wireless/mac80211_hwsim.ko
    drwxr-xr-x  0 root   root        0 Feb  7 13:58 updates/drivers/net/wireless/zd1211rw/
    -rw-r--r--  0 root   root    66996 Feb  7 13:58 updates/drivers/net/wireless/zd1211rw/zd1211rw.ko
    drwxr-xr-x  0 root   root        0 Feb  7 13:58 updates/drivers/net/wireless/rt2x00/
    -rw-r--r--  0 root   root    16346 Feb  7 13:58 updates/drivers/net/wireless/rt2x00/rt2x00usb.ko
    -rw-r--r--  0 root   root    37279 Feb  7 13:58 updates/drivers/net/wireless/rt2x00/rt73usb.ko
    -rw-r--r--  0 root   root    28948 Feb  7 13:58 updates/drivers/net/wireless/rt2x00/rt2500usb.ko
    -rw-r--r--  0 root   root    48044 Feb  7 13:58 updates/drivers/net/wireless/rt2x00/rt2x00lib.ko
    -rw-r--r--  0 root   root    36928 Feb  7 13:58 updates/drivers/net/wireless/rt2x00/rt2800usb.ko
    -rw-r--r--  0 root   root    44305 Feb  7 13:58 updates/drivers/net/wireless/rt2x00/rt2800lib.ko
    drwxr-xr-x  0 root   root        0 Feb  7 13:58 updates/drivers/net/wireless/rtl818x/
    drwxr-xr-x  0 root   root        0 Feb  7 13:58 updates/drivers/net/wireless/rtl818x/rtl8187/
    -rw-r--r--  0 root   root    70408 Feb  7 13:58 updates/drivers/net/wireless/rtl818x/rtl8187/rtl8187.ko
    drwxr-xr-x  0 root   root        0 Feb  7 13:58 updates/drivers/net/wireless/iwmc3200wifi/
    -rw-r--r--  0 root   root    84367 Feb  7 13:58 updates/drivers/net/wireless/iwmc3200wifi/iwmc3200wifi.ko
    drwxr-xr-x  0 root   root        0 Feb  7 13:58 updates/drivers/net/wireless/p54/
    -rw-r--r--  0 root   root    24005 Feb  7 13:58 updates/drivers/net/wireless/p54/p54usb.ko
    -rw-r--r--  0 root   root    38765 Feb  7 13:58 updates/drivers/net/wireless/p54/p54common.ko
    drwxr-xr-x  0 root   root        0 Feb  7 13:58 updates/drivers/net/wireless/ath/
    drwxr-xr-x  0 root   root        0 Feb  7 13:58 updates/drivers/net/wireless/ath/ar9170/
    -rw-r--r--  0 root   root    66585 Feb  7 13:58 updates/drivers/net/wireless/ath/ar9170/ar9170usb.ko
    -rw-r--r--  0 root   root    21409 Feb  7 13:58 updates/drivers/net/wireless/ath/ath.ko
    drwxr-xr-x  0 root   root        0 Feb  7 13:58 updates/drivers/net/wireless/libertas_tf/
    -rw-r--r--  0 root   root    14685 Feb  7 13:58 updates/drivers/net/wireless/libertas_tf/libertas_tf_usb.ko
    -rw-r--r--  0 root   root    22785 Feb  7 13:58 updates/drivers/net/wireless/libertas_tf/libertas_tf.ko
    -rw-r--r--  0 root   root    50092 Feb  7 13:58 updates/drivers/net/wireless/at76c50x-usb.ko
    drwxr-xr-x  0 root   root        0 Feb  7 13:58 updates/drivers/net/wireless/libertas/
    -rw-r--r--  0 root   root    82694 Feb  7 13:58 updates/drivers/net/wireless/libertas/libertas.ko
    -rw-r--r--  0 root   root    17048 Feb  7 13:58 updates/drivers/net/wireless/libertas/libertas_sdio.ko
    -rw-r--r--  0 root   root    17375 Feb  7 13:58 updates/drivers/net/wireless/libertas/usb8xxx.ko
    drwxr-xr-x  0 root   root        0 Feb  7 13:58 updates/drivers/misc/
    drwxr-xr-x  0 root   root        0 Feb  7 13:58 updates/drivers/misc/eeprom/
    -rw-r--r--  0 root   root     3883 Feb  7 13:58 updates/drivers/misc/eeprom/eeprom_93cx6.ko
    drwxr-xr-x  0 root   root        0 Feb  7 13:58 updates/compat/
    -rw-r--r--  0 root   root    18909 Feb  7 13:58 updates/compat/compat.ko
    -rw-r--r--  0 root   root    14947 Feb  7 13:58 updates/compat/compat_firmware_class.ko
    -rw-r--r--  0 root   root    12262 Feb  7 13:58 updates/compat/kfifo.ko
    drwxr-xr-x  0 root   root        0 Feb  7 13:58 updates/net/
    drwxr-xr-x  0 root   root        0 Feb  7 13:58 updates/net/wireless/
    -rw-r--r--  0 root   root     8596 Feb  7 13:58 updates/net/wireless/lib80211_crypt_ccmp.ko
    -rw-r--r--  0 root   root    10459 Feb  7 13:58 updates/net/wireless/lib80211.ko
    -rw-r--r--  0 root   root   183322 Feb  7 13:58 updates/net/wireless/cfg80211.ko
    -rw-r--r--  0 root   root    13044 Feb  7 13:58 updates/net/wireless/lib80211_crypt_tkip.ko
    -rw-r--r--  0 root   root     6852 Feb  7 13:58 updates/net/wireless/lib80211_crypt_wep.ko
    drwxr-xr-x  0 root   root        0 Feb  7 13:58 updates/net/mac80211/
    -rw-r--r--  0 root   root   262335 Feb  7 13:58 updates/net/mac80211/mac80211.ko
    drwxr-xr-x  0 root   root        0 Feb  7 13:58 updates/net/bluetooth/
    drwxr-xr-x  0 root   root        0 Feb  7 13:58 updates/net/bluetooth/hidp/
    -rw-r--r--  0 root   root    24701 Feb  7 13:58 updates/net/bluetooth/hidp/hidp.ko
    -rw-r--r--  0 root   root    90043 Feb  7 13:58 updates/net/bluetooth/bluetooth.ko
    drwxr-xr-x  0 root   root        0 Feb  7 13:58 updates/net/bluetooth/bnep/
    -rw-r--r--  0 root   root    21483 Feb  7 13:58 updates/net/bluetooth/bnep/bnep.ko
    drwxr-xr-x  0 root   root        0 Feb  7 13:58 updates/net/bluetooth/rfcomm/
    -rw-r--r--  0 root   root    53226 Feb  7 13:58 updates/net/bluetooth/rfcomm/rfcomm.ko
    -rw-r--r--  0 root   root    19519 Feb  7 13:58 updates/net/bluetooth/sco.ko
    -rw-r--r--  0 root   root    57433 Feb  7 13:58 updates/net/bluetooth/l2cap.ko
    drwxr-xr-x  0 root   root        0 Feb  7 13:58 updates/net/rfkill/
    -rw-r--r--  0 root   root    29987 Feb  7 13:58 updates/net/rfkill/rfkill_backport.ko

Looking for an indication of the provenance of this set of drivers, 
I see the following text /updates/drivers/net/wireless/rt2x00/rt2800lib.ko:
    license=GPL
    description=Ralink RT2800 library
    version=2.3.0
    author=http://rt2x00.serialmonkey.com, Bartlomiej Zolnierkiewicz
    srcversion=82C305C2B8AEDA2E57F47CE
    depends=rt2x00lib,mac80211,crc-ccitt
    vermagic=2.6.28-chumby preempt mod_unload modversions ARMv5 

According to what I see on the [rt2x00 project website](http://rt2x00.serialmonkey.com/wiki/index.php/Downloads) the sources are only distributed as part of the normal linux kernel source.  Looking at [this patch](http://www.spinics.net/lists/linux-wireless/msg52087.html) I see the source file is /drivers/net/wireless/rt2x00/rt2800lib.c.

Looking at the kernel sources for the [falcon wing 2.8 kernel]() and the parent [imx kernel](https://github.com/clearwater/linux-2.6-imx/tree/imx_2.6.28/drivers/net/wireless/rt2x00) I believe we need an update.

[This post](http://rt2x00.serialmonkey.com/pipermail/users_rt2x00.serialmonkey.com/2011-April/003531.html) suggests that it should be possible to back port the driver to 2.6.28 using the [compat-wireless package](http://linuxwireless.org/en/users/Download/hacking), and I see this update references compat-wireless.  This looks promising as a route to building this driver from source.



