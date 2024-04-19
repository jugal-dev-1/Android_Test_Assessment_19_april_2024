package com.android_test_assessment.ui.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.android_test_assessment.data.model.ApiDateResponse
import com.android_test_assessment.data.model.ApiDateResponseItem
import com.android_test_assessment.data.model.ErrorResponse
import com.android_test_assessment.data.network.common.ERROR_MESSAGE
import com.android_test_assessment.data.network.common.Resource
import com.android_test_assessment.data.repo.UserRepository
import com.android_test_assessment.utils.base.BaseViewModel
import com.android_test_assessment.utils.setError
import com.android_test_assessment.utils.setLoading
import com.android_test_assessment.utils.setSuccess
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class HomeViewModel(private val repository: UserRepository) : BaseViewModel() {
    var _dataListResponse = MutableLiveData<Resource<ApiDateResponse>>()
    val dataListResponseLiveData: LiveData<Resource<ApiDateResponse>> get() = _dataListResponse

    val coroutineExceptionHandler: CoroutineExceptionHandler =
        CoroutineExceptionHandler { a, throwable ->
            println("##Error>${throwable.localizedMessage}")
            _dataListResponse.setError(ERROR_MESSAGE)
        }



    fun callDataListApi() {
        if (isInternetAvailable()) {
            _dataListResponse.setLoading()
            viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
              try {
                  repository.getData().let {
                      if (it.isSuccessful) {
                          it.body()?.let { data ->
                              if (data.isNotEmpty()) {
                                  _dataListResponse.setSuccess(it.body()!!)
                              } else {
                                  if (it.body() != null) {
                                      _dataListResponse.setError(it.body()!!.toString())
                                  } else {
                                      _dataListResponse.setError(it.body()!!)

                                  }
                              }
                          }
                      }
                      else {
                          val gson = Gson()
                          val type = object : TypeToken<ErrorResponse>() {}.type
                          val errorResponse: ErrorResponse? =
                              gson.fromJson(it.errorBody()?.charStream(), type)
                          if (errorResponse?.status == 0) {
                              _dataListResponse.setError(errorResponse.message)
                          } else {
                              _dataListResponse.setError(it.message())
                          }

                      }
                  }
              }catch (e :Exception){
                  println("##Error>${e.localizedMessage}")
              }



            }
        } else {
            _dataListResponse.setError("Make sure you have an active data connection")
        }

    }

}