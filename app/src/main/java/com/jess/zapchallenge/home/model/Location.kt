package com.jess.zapchallenge.home.model


import com.google.gson.annotations.SerializedName

data class Location(
    @SerializedName("lat")
    val lat: Double = 0.0,
    @SerializedName("lon")
    val lon: Double = 0.0
)