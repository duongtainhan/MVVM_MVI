package com.dienty.structure.extensions

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.core.content.ContextCompat

inline fun <reified ACTIVITY : Activity> newIntent(context: Context): Intent = Intent(context, ACTIVITY::class.java)

inline fun <reified ACTIVITY : Activity> Activity.launchActivity(
    requestCode: Int = -1,
    options: Bundle? = null,
    noinline init: Intent.() -> Unit = {}
) {
    val intent = newIntent<ACTIVITY>(this)
    intent.init()
    intent.data = this.intent.data
    startActivityForResult(intent, requestCode, options)
}

internal fun Activity.toast(message: String, durationMs: Int = Toast.LENGTH_SHORT) {
    val toast = Toast.makeText(this, message, durationMs)
    toast.show()
    if (durationMs > 1) {
        Handler(Looper.getMainLooper()).postDelayed({
            toast.cancel()
        }, durationMs.toLong())
    }
}

internal fun Activity.toast(messageResId: Int, durationMs: Int = Toast.LENGTH_SHORT) {
    toast(getString(messageResId), durationMs)
}

internal fun Activity.getDimensInt(resId: Int): Int {
    return applicationContext.resources.getDimensionPixelSize(resId)
}

internal fun Activity.getDimensFloat(resId: Int): Float {
    return applicationContext.resources.getDimension(resId)
}

internal fun Activity.drawable(id: Int): Drawable? {
    return ContextCompat.getDrawable(applicationContext, id)
}