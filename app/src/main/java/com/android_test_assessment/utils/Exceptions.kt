package com.android_test_assessment.utils

import java.io.IOException

class ApiException(message: String) : IOException(message)
class NoInternetException(message: String) : IOException(message)
class UnotheriseException(message: String) : IOException(message)