package com.jess.zapchallenge.home.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PricingInfos(
    @SerializedName("businessType")
    val businessType: String = "",
    @SerializedName("monthlyCondoFee")
    val monthlyCondoFee: String = "",
    @SerializedName("price")
    val price: String = "",
    @SerializedName("yearlyIptu")
    val yearlyIptu: String = ""
): Parcelable