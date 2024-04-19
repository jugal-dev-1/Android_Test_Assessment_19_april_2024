package com.android_test_assessment.data.network


import com.android_test_assessment.data.model.ApiDateResponse
import com.android_test_assessment.data.network.common.DATA_API
import retrofit2.Response
import retrofit2.http.*


interface ApiInterface {
    @GET(DATA_API)
    suspend fun getDataRequest(): Response<ApiDateResponse>


}