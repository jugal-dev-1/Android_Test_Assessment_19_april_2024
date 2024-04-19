package com.android_test_assessment.utils

import android.content.Context
import android.provider.Settings
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar


fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun ProgressBar.show() {
    visibility = View.VISIBLE
}

fun ProgressBar.hide() {
    visibility = View.GONE
}

fun View.snackbar(message: String) {
    Snackbar.make(this, message, Snackbar.LENGTH_LONG).also { snackbar ->
        snackbar.setAction("Ok") {
            snackbar.dismiss()

        }
        val snackbarView = snackbar.view
        val tv =
            snackbarView.findViewById<View>(com.google.android.material.R.id.snackbar_text) as TextView
        tv.maxLines = 3
        snackbar.show()
    }.show()
}

fun View.showMessage(message: Any) {
    val stMsg = when (message) {
        is String -> message.toString()
        is Int -> resources.getString(message)
        else -> ""
    }

    findViewById<View>(android.R.id.content).apply {
        snackbar(stMsg)
    }
}


fun checkStringValue(value: String?, blank: String = ""): String {
    if (value.isNullOrEmpty()) {
        return blank
    }
    return value
}


fun getDeviceId(mContext: Context): String {
    return Settings.Secure.getString(
        mContext.contentResolver,
        Settings.Secure.ANDROID_ID
    )
}