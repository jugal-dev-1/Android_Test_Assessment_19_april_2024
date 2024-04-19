package com.android_test_assessment.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.Gravity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.google.android.material.snackbar.Snackbar
import java.util.regex.Matcher
import java.util.regex.Pattern


fun View.isViewHide(): Boolean {
    return this.visibility == View.GONE
}


fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun View.hide() {
    this.visibility = View.GONE
}

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.showOnlyWhen(isShow: Boolean) {
    if (isShow)
        this.visibility = View.VISIBLE
    else
        this.visibility = View.GONE
}

fun View.hideKeyboard() {
    val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(this.windowToken, 0)
}

fun View.showKeyboard() {
    val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
}

fun View?.showSnackBar(message: String?, icon: Int = 0) {
    if (this != null && message.checkNotEmpty()) {
        Snackbar.make(this, message!!, Snackbar.LENGTH_SHORT).apply {
            addIconIfNeeded(icon)
            show()
        }
    }
}

fun String?.checkNotEmpty(): Boolean {
    return this != null && isNotEmpty() && isNotBlank()
}





fun View?.showSnackBar(message: Int, icon: Int = 0) {
    this ?: return

    Snackbar.make(this, message, Snackbar.LENGTH_SHORT).apply {
        addIconIfNeeded(icon)
        show()
    }
}

fun Snackbar.addIconIfNeeded(icon: Int = 0) {
    if (icon != 0) {
        val snackBarLayout = view
        val textView =
            snackBarLayout.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
        textView.gravity = Gravity.CENTER_VERTICAL
        textView.setCompoundDrawablesWithIntrinsicBounds(icon, 0, 0, 0)
        textView.compoundDrawablePadding = 10
    }
}

inline fun <reified T : Any> Context.launchActivity(
    removeFromStack: Boolean = false,
    options: Bundle? = null,
    noinline init: Intent.() -> Unit = {},
) {

    val intent = newIntent<T>(this)
    intent.init()
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
        startActivity(intent, options)
    } else {
        startActivity(intent)
    }
}

inline fun <reified T : Any> Activity.launchActivityWithFinish(
    removeFromStack: Boolean = false,
    options: Bundle? = null,
    noinline init: Intent.() -> Unit = {},
) {

    val intent = newIntent<T>(this)
    intent.init()
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
        startActivity(intent, options)
    } else {
        startActivity(intent)
    }
    this.finish()
}

inline fun <reified T : Any> newIntent(context: Context): Intent =
    Intent(context, T::class.java)


fun String.isValidEmail(): Boolean {
    return !TextUtils.isEmpty(this) && Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

fun String.isValidUserName(): Boolean {
    // Regex to check valid username.
    val regex = "^[A-Za-z]\\w{2,29}$"

    // Compile the ReGex
    val p = Pattern.compile(regex)

    // If the username is empty
    // return false
    if (this == null) {
        return false
    }

    // Pattern class contains matcher() method
    // to find matching between given username
    // and regular expression.
    val m = p.matcher(this)

    // Return if the username
    // matched the ReGex
    return m.matches()
}


/**
 * Check Mobile number is valid or not
 */
fun String.isValidMobile(): Boolean {
    return Patterns.PHONE.matcher(this).matches()
}

fun String.IsValidPassword(): Boolean {
    val pattern: Pattern
//    val PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=?])(?=\\S+$).{4,}$"
    val PASSWORD_PATTERN = "^(?=\\S+$).{6,}$"
    pattern = Pattern.compile(PASSWORD_PATTERN)
    val matcher: Matcher = pattern.matcher(this)
    return matcher.matches()
}

fun FragmentActivity.replaceFragment(
    @IdRes containerViewId: Int,
    fragment: Fragment,
    isFirst: Boolean = false,
) {
    if (isFirst) {
        supportFragmentManager
            .beginTransaction()
            .replace(containerViewId, fragment)
            .commit()
    } else {
        supportFragmentManager
            .beginTransaction()
            .replace(containerViewId, fragment)
            .addToBackStack(null)
            .commit()
    }
}

fun View.visible(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}

