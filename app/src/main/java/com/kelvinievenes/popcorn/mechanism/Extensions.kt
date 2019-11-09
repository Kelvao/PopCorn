package com.kelvinievenes.popcorn.mechanism

import android.app.Activity
import android.graphics.Point

fun Activity.screenHeight(): Int {
    val display = windowManager.defaultDisplay
    val size = Point()
    display.getSize(size)
    return size.y
}