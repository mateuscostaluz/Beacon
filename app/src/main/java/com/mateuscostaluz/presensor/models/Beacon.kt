package com.mateuscostaluz.presensor.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*
@Parcelize
data class Beacon(var uuid: String, var mac: String) : Parcelable