package com.mateuscostaluz.presensor.models

import com.google.gson.annotations.SerializedName
import java.util.*

data class JWT(@SerializedName("token") var jwt: String)