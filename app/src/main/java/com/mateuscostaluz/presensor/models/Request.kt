package com.mateuscostaluz.presensor.models

import com.google.gson.annotations.SerializedName

data class Request<T>(
    var status: Int,
    var message: String,
    var data: T
)