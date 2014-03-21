

EXTRA_OECONF_qemux86 += "-qt-xcb -qpa xcb -opengl es2 -no-eglfs  -compile-examples"
RDEPENDS_${PN}_append_qemux86 += "${XSERVER}  xf86-video-vesa libgl-mesa mesa-driver-swrast"
