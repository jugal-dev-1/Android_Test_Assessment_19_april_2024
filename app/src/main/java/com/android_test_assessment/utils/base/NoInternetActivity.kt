package com.android_test_assessment.utils.base

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.android_test_assessment.R
import com.android_test_assessment.data.network.common.CommonMethod
import com.android_test_assessment.databinding.ActivityNoInternetBinding

class NoInternetActivity : BaseActivity() {

    private lateinit var binding: ActivityNoInternetBinding
    var isConnectionOn: Boolean = false

    override fun getContentResource(): View {
        binding = ActivityNoInternetBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onClickSafe(view: View) {
        when (view.id) {
            R.id.btnTryAgain -> if (isConnectionOn) finish()

        }

    }

    override fun onInternetConnectionChange(isConnection: Boolean) {
        super.onInternetConnectionChange(isConnection)
        CommonMethod.checkNetworkConnection(this, isConnection)
        isConnectionOn = isConnection
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }

    override fun initViews() {
        binding.btnTryAgain.setOnClickListener(this)

    }
}