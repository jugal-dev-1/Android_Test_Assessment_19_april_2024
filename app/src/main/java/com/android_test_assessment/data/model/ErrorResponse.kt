package com.android_test_assessment.data.model

import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @SerializedName("result") var status: Int,
    @SerializedName("msg") var message: String,
)