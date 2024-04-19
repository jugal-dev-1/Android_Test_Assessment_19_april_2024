package com.android_test_assessment.ui.home

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.android_test_assessment.data.network.common.ProgressDialogUtil
import com.android_test_assessment.databinding.ActivityHomeBinding
import com.android_test_assessment.utils.base.BaseActivity

class HomeActivity : BaseActivity() {
    private lateinit var binding: ActivityHomeBinding

    override fun getContentResource(): View {
        binding = ActivityHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onClickSafe(view: View) {
    }

    override fun initViews() {
        replaceFragment(binding.homeContainer.id, HomeFragment(), true)
    }
}