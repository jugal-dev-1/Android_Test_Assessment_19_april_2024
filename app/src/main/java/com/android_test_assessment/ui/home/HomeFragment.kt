package com.android_test_assessment.ui.home

import android.nfc.tech.MifareUltralight.PAGE_SIZE
import android.os.Handler
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android_test_assessment.R
import com.android_test_assessment.data.network.ApiClient
import com.android_test_assessment.data.network.common.ProgressDialogUtil
import com.android_test_assessment.data.repo.UserRepository
import com.android_test_assessment.databinding.FragmentHomeBinding
import com.android_test_assessment.ui.home.adapter.ItemListAdapter
import com.android_test_assessment.utils.Status
import com.android_test_assessment.utils.base.BaseFragment
import com.android_test_assessment.utils.base.BaseViewModelFactory
import com.android_test_assessment.utils.hide
import com.android_test_assessment.utils.replaceFragment
import com.android_test_assessment.utils.show


class HomeFragment : BaseFragment() {
    lateinit var binding: FragmentHomeBinding
//    private var offset = 0
//    private val limit = 10
    private var isLoading = false
    private var isLastPage = false
    private var currentPage = 1
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var itemListAdapter: ItemListAdapter
    override fun bind(container: ViewGroup?): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun initView() {
        homeViewModel = ViewModelProviders.of(
            this,
            BaseViewModelFactory(HomeViewModel(UserRepository(ApiClient().getApiInstance())))
        ).get(HomeViewModel::class.java)
        setObserver()
        itemListAdapter = ItemListAdapter(requireContext()) { data, position ->
//click of item
            activity?.replaceFragment(R.id.homeContainer, ItemDetailFragment(data))
        }


        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvData.layoutManager = layoutManager

        binding.rvData.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                if (!isLoading && !isLastPage) {
                    if (visibleItemCount + firstVisibleItemPosition >= totalItemCount
                        && firstVisibleItemPosition >= 0
                        && totalItemCount >= PAGE_SIZE
                    ) {
//                        loadMoreItems()
                    }
                }
            }
        })


        homeViewModel.callDataListApi()
    }
    private fun loadItems() {
        // Perform data loading here (e.g., from API or local database)
        // Update adapter with loaded data
    }

    private fun loadMoreItems() {
        // Perform loading of more items (e.g., fetch next page from API)
        isLoading = true
        currentPage++

        // Simulate loading delay
        Handler().postDelayed({
            // Load more data and update adapter
            // Example: apiService.fetchData(currentPage)
            // Update adapter with newly loaded data
            isLoading = false
            // Check if reached last page
            // isLastPage = if (currentPage == totalPages) true else false
        }, 1000) // Delay for demonstration purposes, replace with actual loading logic
    }



    override fun onClickSafe(view: View) {
    }

    private fun setObserver() {
        homeViewModel.dataListResponseLiveData.observe(this) {
            when (it.status) {
                Status.LOADING -> {
                    ProgressDialogUtil.showProgressDialog(requireContext())
                }

                Status.SUCCESS -> {
                    ProgressDialogUtil.hideProgressDialog()
                    if (it?.item != null) {
                        if (it.item!!.isEmpty()) {
                            binding.rvData.hide()

                        } else {
                            binding.rvData.show()
                            itemListAdapter.addAll(it.item!!)
                            binding.rvData.adapter = itemListAdapter
                        }
                    } else {
                        binding.rvData.hide()
                    }

                }

                Status.ERROR -> {
                    ProgressDialogUtil.hideProgressDialog()
                    showMessage(it.message)
                    binding.rvData.hide()
                }
            }
        }

    }
}