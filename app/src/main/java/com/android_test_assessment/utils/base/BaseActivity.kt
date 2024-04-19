package com.android_test_assessment.utils.base

import android.content.Context
import android.content.pm.ActivityInfo
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.annotation.CallSuper
import androidx.annotation.IdRes
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.android_test_assessment.R
import com.android_test_assessment.data.network.common.CommonMethod
import com.android_test_assessment.utils.snackbar
import com.android_test_assessment.instanceApp
import com.google.android.material.bottomnavigation.BottomNavigationView


abstract class BaseActivity : AppCompatActivity(), View.OnClickListener {


    companion object {
        var dialogShowing = false
        var navigationState: BottomNavigationView? = null
    }

    lateinit var viewModelFactory: ViewModelProvider.Factory
//    lateinit var baseViewModel: BaseViewModel

    private val connectivityManager: ConnectivityManager by lazy {
        getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    }
    private val builder: NetworkRequest.Builder by lazy {
        NetworkRequest.Builder()
    }
    private val networkCallBack: NetworkCallback by lazy {
        object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                onInternetConnectionChange(true)
            }

            override fun onLost(network: Network) {
                super.onLost(network)
                onInternetConnectionChange(false)
            }

        }

    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onStart() {
        connectivityManager.registerNetworkCallback(builder.build(), networkCallBack)
        if (connectivityManager.activeNetwork != null) {
            onInternetConnectionChange(true)
        }
        super.onStart()

    }

    override fun onStop() {
        connectivityManager.unregisterNetworkCallback(networkCallBack)
        super.onStop()
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getContentResource())
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        initViews()
        changesStatusBarColor()
        setObserver()

    }

    override fun onResume() {
        super.onResume()

        connectivityManager.registerNetworkCallback(builder.build(), networkCallBack)

    }

    override fun onDestroy() {
        super.onDestroy()

    }

    protected abstract fun getContentResource(): View
    private fun setObserver() {
//        baseViewModel.baseResponseLiveData.observe(this, {
//            when (it.status) {
//                Status.LOADING -> {
//                    ProgressDialogUtil.showProgressDialog(this)
//                }
//                Status.SUCCESS -> {
//                    ProgressDialogUtil.hideProgressDialog()
//                    showMessage(it.message)
//                }
//                Status.ERROR -> {
//                    showMessage(it.message)
//                    ProgressDialogUtil.hideProgressDialog()
//                }
//            }
//        })
    }

    fun showMessage(message: Any) {
        val stMsg = when (message) {
            is String -> message.toString()
            is Int -> resources.getString(message)
            else -> ""
        }

        findViewById<View>(android.R.id.content).apply {
            snackbar(stMsg)
        }
    }


    override fun onClick(v: View?) {
        v?.let {
//            v.setSafeOnClickListener {
            onClickSafe(it)
//            }
        }
    }

    fun replaceFragment(@IdRes containerViewId: Int, fragment: Fragment, isFirst: Boolean = false) {
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

    /**
     * Double tap to Exit application
     */
    private var backCheck = false
    fun doubleTapToExit() {
        if (backCheck) {
            finishAffinity()
            return
        }
        backCheck = true
        showMessage(resources.getString(R.string.double_tap_exit))
        Handler().postDelayed({ backCheck = false; }, 2000)
    }

    protected abstract fun onClickSafe(view: View)

    fun changesStatusBarColor(color: Int = R.color.black) {
        val window: Window = this.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = this.resources.getColor(R.color.black)
    }



    @RequiresApi(Build.VERSION_CODES.M)
    fun isInternetAvailable(): Boolean {
        var result = false
        val connectivityManager =
            instanceApp.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        connectivityManager?.let {
            it.getNetworkCapabilities(connectivityManager.activeNetwork)?.apply {
                result = when {
                    hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                    hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                    else -> false
                }
            }
        }
        return result
    }

    override fun onPause() {
        super.onPause()

    }

    abstract fun initViews()


    @CallSuper
    protected open fun onInternetConnectionChange(isConnection: Boolean = false) {
        CommonMethod.checkNetworkConnection(this, isConnection)
    }
}