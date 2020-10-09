package com.jess.zapchallenge.home.model


import com.google.gson.annotations.SerializedName

data class GeoLocation(
    @SerializedName("location")
    val location: Location = Location(),
    @SerializedName("precision")
    val precision: String = ""
)