package com.jess.zapchallenge.home.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Location(
    @SerializedName("lat")
    val lat: Double = 0.0,
    @SerializedName("lon")
    val lon: Double = 0.0
): Parcelable