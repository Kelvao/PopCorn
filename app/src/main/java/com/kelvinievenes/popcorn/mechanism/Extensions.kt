package com.kelvinievenes.popcorn.mechanism

import android.app.Activity
import android.graphics.Point
import android.view.inputmethod.InputMethodManager

fun Activity.screenHeight(): Int {
    val display = windowManager.defaultDisplay
    val size = Point()
    display.getSize(size)
    return size.y
}

fun Activity.hideKeyboard() {
    val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(window.decorView.applicationWindowToken, 0)
}