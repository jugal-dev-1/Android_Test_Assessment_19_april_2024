package com.android_test_assessment.utils.base

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.android_test_assessment.data.network.common.CommonMethod
import com.android_test_assessment.data.network.common.ProgressDialogUtil
import com.android_test_assessment.utils.snackbar

abstract class BaseFragment : Fragment(), View.OnClickListener {

    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val connectivityManager: ConnectivityManager by lazy {
        requireActivity().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    }
    private val networkBuilder: NetworkRequest.Builder by lazy {
        NetworkRequest.Builder()
    }
    private val networkCallBack: ConnectivityManager.NetworkCallback by lazy {
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

    override fun onStop() {
        connectivityManager.unregisterNetworkCallback(networkCallBack)
        super.onStop()
    }

    @CallSuper
    protected open fun onInternetConnectionChange(isConnection: Boolean = false) {
        CommonMethod.checkNetworkConnection(requireActivity(), isConnection)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        return bind(container)
    }

    override fun onClick(v: View?) {
        v?.let {
//            v.setSafeOnClickListener {
            onClickSafe(it)
//            }
        }
    }

    fun showMessage(message: Any) {
        val stMsg = when (message) {
            is String -> message.toString()
            is Int -> resources.getString(message)
            else -> ""
        }

        this.requireActivity().findViewById<View>(android.R.id.content).apply {
            snackbar(stMsg)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()

    }

    protected abstract fun bind(container: ViewGroup?): View?
    protected abstract fun initView()
    protected abstract fun onClickSafe(view: View)

    fun showProgress() {
        if (!this.requireActivity().isFinishing) {
            ProgressDialogUtil?.showProgressDialog(this.requireContext())
            // progress?.show()
        }
    }

    override fun onStart() {
        connectivityManager.registerNetworkCallback(networkBuilder.build(), networkCallBack)
        if (connectivityManager.activeNetwork == null) {
            onInternetConnectionChange(false)
        }
        super.onStart()

    }

    fun hideProgress() {
        ProgressDialogUtil?.hideProgressDialog()

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is Activity) {
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

    override fun onResume() {
        super.onResume()


    }

    override fun onDestroy() {
        super.onDestroy()

    }




    protected fun showDialogFragment(dialogFragment: DialogFragment, tag: String) {
        if (!dialogFragment.isAdded) {
            dialogFragment.show(childFragmentManager, tag)
        }
    }


}
