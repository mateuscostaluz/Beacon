package com.mateuscostaluz.presensor.models

import com.google.gson.annotations.SerializedName
import java.util.*

data class DayWeek(var id: Int, @SerializedName("dia")var day: String)