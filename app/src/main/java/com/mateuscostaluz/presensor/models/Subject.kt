package com.mateuscostaluz.presensor.models

import com.google.gson.annotations.SerializedName

data class Subject(
    @SerializedName("sigla") var initials: String,
    @SerializedName("nome") var name: String
)