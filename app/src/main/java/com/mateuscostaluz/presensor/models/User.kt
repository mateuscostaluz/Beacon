package com.mateuscostaluz.presensor.models

import com.google.gson.annotations.SerializedName
import java.util.*

data class User(var email: String, @SerializedName("senha")var password: String)