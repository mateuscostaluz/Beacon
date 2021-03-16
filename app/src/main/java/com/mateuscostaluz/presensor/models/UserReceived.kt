package com.mateuscostaluz.presensor.models

import com.google.gson.annotations.SerializedName

data class UserReceived(
    var ra: String="",
    var email: String="",
    @SerializedName("senha") var password: String = "",
    @SerializedName("nome") var name: String = ""
)