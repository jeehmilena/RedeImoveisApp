package com.jess.zapchallenge.home.model


import com.google.gson.annotations.SerializedName

data class Address(
    @SerializedName("city")
    val city: String = "",
    @SerializedName("geoLocation")
    val geoLocation: GeoLocation = GeoLocation(),
    @SerializedName("neighborhood")
    val neighborhood: String = ""
)