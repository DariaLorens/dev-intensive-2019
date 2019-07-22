package ru.skillbranch.devintensive.extensions

import android.app.Activity
import android.graphics.Rect
import android.view.View
import android.view.inputmethod.InputMethodManager

fun Activity.hideKeyboard() {
    val imm = this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    //Find the currently focused view, so we can grab the correct window token from it.
    var view = this.currentFocus
    //If no view currently has focus, create a new one, just so we can grab a window token from it
    if (view == null) {
        view = View(this)
    }
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Activity.isKeyboardOpen(): Boolean {
    val rootView = this.findViewById<View>(android.R.id.content)

    val visibleBounds = Rect()
    rootView.getWindowVisibleDisplayFrame(visibleBounds)

    val heightDiff = rootView.height - visibleBounds.height()
    val marginOfError = 10
    return heightDiff > marginOfError
}

fun Activity.isKeyboardClosed(): Boolean {
    return !isKeyboardOpen()
}