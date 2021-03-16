package com.mateuscostaluz.presensor.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue
@Parcelize
data class BeaconReceived(
    var id: String,
    @SerializedName("disciplina") var subject: @RawValue Subject,
    @SerializedName("sala") var classRoom: @RawValue ClassRoom,
    @SerializedName("diaSemana") var dayWeek:@RawValue DayWeek,
    @SerializedName("horarioInicio") var initialHour: String,
    @SerializedName("horarioFim") var endHour: String
): Parcelable