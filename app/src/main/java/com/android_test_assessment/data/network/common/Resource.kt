package com.android_test_assessment.data.network.common

import com.android_test_assessment.utils.Status


class Resource<T>(val status: Status) {

    var item: T? = null
    var throwable: Throwable? = null
    var message: String = ""

    constructor(status: Status, item: T) : this(status) {
        this.item = item
    }

    constructor(status: Status, message:String) : this(status) {
        this.message = message
    }

    constructor(status: Status, throwable: Throwable?) : this(status) {
        this.throwable = throwable
    }
}