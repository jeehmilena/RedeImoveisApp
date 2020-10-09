package com.jess.zapchallenge.home.model


import com.google.gson.annotations.SerializedName

data class PricingInfos(
    @SerializedName("businessType")
    val businessType: String = "",
    @SerializedName("monthlyCondoFee")
    val monthlyCondoFee: String = "",
    @SerializedName("price")
    val price: String = "",
    @SerializedName("yearlyIptu")
    val yearlyIptu: String = ""
)