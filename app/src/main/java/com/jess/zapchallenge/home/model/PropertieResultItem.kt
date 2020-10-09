package com.jess.zapchallenge.home.model


import com.google.gson.annotations.SerializedName

data class PropertieResultItem(
    @SerializedName("address")
    val address: Address = Address(),
    @SerializedName("bathrooms")
    val bathrooms: Int = 0,
    @SerializedName("bedrooms")
    val bedrooms: Int = 0,
    @SerializedName("createdAt")
    val createdAt: String = "",
    @SerializedName("id")
    val id: String = "",
    @SerializedName("images")
    val images: List<String> = listOf(),
    @SerializedName("listingStatus")
    val listingStatus: String = "",
    @SerializedName("listingType")
    val listingType: String = "",
    @SerializedName("owner")
    val owner: Boolean = false,
    @SerializedName("parkingSpaces")
    val parkingSpaces: Int = 0,
    @SerializedName("pricingInfos")
    val pricingInfos: PricingInfos = PricingInfos(),
    @SerializedName("updatedAt")
    val updatedAt: String = "",
    @SerializedName("usableAreas")
    val usableAreas: Int = 0
)