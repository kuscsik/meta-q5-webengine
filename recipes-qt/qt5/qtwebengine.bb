SUMMARY = "Qt Web Engine"
DESCRIPTION = "QtWebEngine is an attempt to integrate chromium’s fast moving web capabilities into Qt."
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM="file://src/3rdparty/ninja/COPYING;md5=a81586a64ad4e476c791cda7e2f2c52e"
SRCREV = "${AUTOREV}"
S = "${WORKDIR}/git"

DEPENDS = "qtbase"

inherit qmake5_base

EXTRA_QMAKEVARS_PRE += "target.path=${libdir}/${P}"

export QMAKESPEC = "${QMAKE_MKSPEC_PATH_NATIVE}/mkspecs/${OE_QMAKE_PLATFORM_NATIVE}"
export XQMAKESPEC = "${QMAKE_MKSPEC_PATH}/mkspecs/${OE_QMAKE_PLATFORM}"
export QMAKE_QMAKE = "${STAGING_BINDIR_NATIVE}/${QT_DIR_NAME}/qmake"
export QMAKE_COMPILER = "${CC}"
export QMAKE_CC = "${CC}"
export QMAKE_CFLAGS = "${CFLAGS}"
export QMAKE_CXX = "${CXX}"
export QMAKE_CXXFLAGS = "${CXXFLAGS}"
export QMAKE_LINK = "${CXX}"
export QMAKE_LDFLAGS = "${LDFLAGS}"
export QMAKE_AR = "${AR}"
export QMAKE_STRIP = "echo"
export QMAKE_WAYLAND_SCANNER = "${STAGING_BINDIR_NATIVE}/wayland-scanner"

DEPENDS += "virtual/gettext nss gnome-keyring-1 gtk+ cups krb5"

# other version available for small screens
SRC_URI = "git://git.gitorious.org/qt-labs/qtwebengine.git"
SRC_URI[md5sum] = ""
SRC_URI[sha256sum] = ""

PACKAGE_DEBUG_SPLIT_STYLE = "debug-without-src"

require recipes-qt/qt5/qt5.inc

FILES_${PN}-dbg += "${datadir}/${P}/.debug"
FILES_${PN} += "${datadir}"


# Chromium requires msse and msse2 on x86

LOCAL_X86_CFLAGS = "-msse2 -msse"
LOCAL_X86_CCFLAGS = "-msse2 -msse"

do_patch(){
    cd ${S}
    ./init-repository.py
}

do_configure(){
    cd ${S}
    # TODO: QtWebEngine build scripts fail to expand the ${OE_QMAKE_...}
    # variables, so we need to pass them explicitely to qmake
    qmake -r CONFIG-=silent QMAKE_CXX="${OE_QMAKE_CXX}" QMAKE_CC="${OE_QMAKE_CC}"\
        QMAKE_LINK="${OE_QMAKE_LINK}"\
        QMAKE_CFLAGS="${OE_QMAKE_CFLAGS} -msse2 -msse"\
        QMAKE_CXXFLAGS="${OE_QMAKE_CXXFLAGS} -msse2 -msse"\
        QMAKE_AR="${OE_QMAKE_AR}"
}

do_compile(){
    cd ${S}
    make -B
}

do_install_prepend() {
    cd ${S}
}
