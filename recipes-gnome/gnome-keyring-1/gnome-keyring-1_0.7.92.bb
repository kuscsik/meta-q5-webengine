LICENSE = "GPL"
SECTION = "x11/gnome"
SRC_URI = "http://ftp.gnome.org/pub/GNOME/sources/gnome-keyring/0.7/gnome-keyring-${PV}.tar.bz2 \
           file://0001_fix_cr_size.patch \
    "

SRC_URI[md5sum] = "ba7acfe9a8acd9bfdb97f403a42a162e"
LIC_FILES_CHKSUM = "file://COPYING;md5=94d55d512a9ba36caa9b7df079bae19f"

S = "${WORKDIR}/gnome-keyring-${PV}"
inherit autotools pkgconfig

DEPENDS = "gtk+"

EXTRA_OECONF = "--disable-gtk-doc"

HEADERS = " \
gnome-keyring.h \
"
do_configure() {
    ./configure
}

do_install() {
    install -d ${STAGING_INCDIR}/gnome-keyring-1
    for i in ${HEADERS}; do
        install -m 0644 $i ${STAGING_INCDIR}/gnome-keyring-1/$i
    done
    oe_libinstall -so libgnome-keyring ${STAGING_LIBDIR}
    install -D ${S}/gnome-keyring-1.pc ${D}${libdir}/pkgconfig/gnome-keyring-1.pc
}

addtask do_devshell before do_compile
