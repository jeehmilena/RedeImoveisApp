package com.jess.zapchallenge.home.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Address(
    @SerializedName("city")
    val city: String = "",
    @SerializedName("geoLocation")
    val geoLocation: GeoLocation = GeoLocation(),
    @SerializedName("neighborhood")
    val neighborhood: String = ""
): Parcelable