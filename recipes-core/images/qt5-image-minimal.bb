SUMMARY = "Qt5 minimal image"

IMAGE_FEATURES += "package-management"
IMAGE_INSTALL = "packagegroup-core-boot ${ROOTFS_PKGMANAGE_BOOTSTRAP} ${CORE_IMAGE_EXTRA_INSTALL}"

inherit core-image

IMAGE_INSTALL += " \
    qtbase \
    qtbase-plugins\
    cinematicexperience \
    qtwebengine\
    dropbear\
    "

IMAGE_ROOTFS_SIZE = "8192"

