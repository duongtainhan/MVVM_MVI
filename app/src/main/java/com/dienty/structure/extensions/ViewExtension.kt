package com.dienty.structure.extensions

import android.animation.Animator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

/**
 * Toast message from View (in customView ...)
 */
internal fun View.toast(message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

/**
 * Fast way to set view visibility
 */
fun View.show() {
    post { visibility = View.VISIBLE }
}

fun View.gone() {
    post { visibility = View.GONE }
}

fun View.hide() {
    post { visibility = View.INVISIBLE }
}
