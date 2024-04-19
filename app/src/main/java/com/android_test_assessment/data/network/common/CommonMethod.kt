package com.android_test_assessment.data.network.common

import android.app.Activity
import android.content.Intent
import com.android_test_assessment.utils.base.NoInternetActivity

object CommonMethod {

    fun checkNetworkConnection(activity: Activity, isConnectionOn: Boolean) {
        if (!isConnectionOn) {
            activity.startActivity(Intent(activity, NoInternetActivity::class.java))
        } else {
            if (activity is NoInternetActivity) activity.finish()
        }
    }
}