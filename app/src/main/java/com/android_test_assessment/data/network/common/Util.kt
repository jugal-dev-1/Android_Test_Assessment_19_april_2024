package com.android_test_assessment.data.network.common

import android.content.Context
import android.net.ConnectivityManager

object Util {

    fun checkNetwork(context: Context): Boolean {
        return (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)
            .activeNetworkInfo?.isConnected == true
    }

}