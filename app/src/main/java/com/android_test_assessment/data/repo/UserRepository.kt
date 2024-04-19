package com.android_test_assessment.data.repo



import com.android_test_assessment.data.network.ApiInterface
import com.android_test_assessment.data.network.SafeApiRequest


class UserRepository(
    private val apiInterface: ApiInterface
) : SafeApiRequest() {

    suspend fun getData() =
        apiInterface.getDataRequest()
}