package com.jess.zapchallenge.home.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GeoLocation(
    @SerializedName("location")
    val location: Location = Location(),
    @SerializedName("precision")
    val precision: String = ""
): Parcelable