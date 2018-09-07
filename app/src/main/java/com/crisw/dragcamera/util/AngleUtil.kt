package com.crisw.dragcamera.util

object AngleUtil {
    fun getSensorAngle(x: Float, y: Float): Int {
        return if (Math.abs(x) > Math.abs(y)) {
            if (x > 4) {
                270
            } else if (x < -4) {
                90
            } else {
                0
            }
        } else {
            if (y > 7) {
                0
            } else if (y < -7) {
                180
            } else {
                0
            }
        }
    }
}
