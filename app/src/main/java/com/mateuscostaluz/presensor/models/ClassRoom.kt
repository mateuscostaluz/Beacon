package com.mateuscostaluz.presensor.models

import com.google.gson.annotations.SerializedName
import java.util.*

data class ClassRoom(var uuidBeacon: String, @SerializedName("numero")var number: String)