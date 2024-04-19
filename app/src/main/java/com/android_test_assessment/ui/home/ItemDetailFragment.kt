package com.android_test_assessment.ui.home

import android.view.View
import android.view.ViewGroup
import com.android_test_assessment.data.model.ApiDateResponseItem
import com.android_test_assessment.databinding.FragmentItemDetailBinding
import com.android_test_assessment.utils.base.BaseFragment


class ItemDetailFragment(val data: ApiDateResponseItem) : BaseFragment() {
    lateinit var binding: FragmentItemDetailBinding

    override fun bind(container: ViewGroup?): View? {
        binding = FragmentItemDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun initView() {

        if (data != null){
            binding.tvId.text= "User id :- ${data.userId}  id:-${data.id}"
            binding.tvTitle.text= data.title
            binding.tvBody.text= data.body
        }
    }

    override fun onClickSafe(view: View) {
    }


}