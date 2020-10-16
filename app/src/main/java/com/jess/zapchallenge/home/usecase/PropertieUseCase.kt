package com.jess.zapchallenge.home.usecase

import com.jess.zapchallenge.Constants.MAX_LAT
import com.jess.zapchallenge.Constants.MAX_LON
import com.jess.zapchallenge.Constants.MAX_RENTAL_PRICE
import com.jess.zapchallenge.Constants.MAX_SALE_PRICE
import com.jess.zapchallenge.Constants.MIN_LAT
import com.jess.zapchallenge.Constants.MIN_LON
import com.jess.zapchallenge.Constants.MIN_RENTAL_PRICE
import com.jess.zapchallenge.Constants.MIN_SALE_PRICE
import com.jess.zapchallenge.Constants.PERCENTAGE_MONTHLY_CONDO_FEE
import com.jess.zapchallenge.Constants.PERCENTAGE_RENTAL
import com.jess.zapchallenge.Constants.PERCENTAGE_SALE
import com.jess.zapchallenge.Constants.RENTAL
import com.jess.zapchallenge.Constants.SALE
import com.jess.zapchallenge.Constants.USABLE_AREAS_MIN
import com.jess.zapchallenge.home.model.PropertieResultItem

class PropertieUseCase {

    fun listGroupZap(list: ArrayList<PropertieResultItem>): ArrayList<PropertieResultItem> {
        val filteredList = arrayListOf<PropertieResultItem>()

        list.forEach {
            if (eligibleForZap(it) && verifyLatLon(it)) {
                boundingBoxZap(it)
                filteredList.add(it)
            }
        }
        return filteredList
    }

    fun listGroupVivaReal(list: ArrayList<PropertieResultItem>): ArrayList<PropertieResultItem> {
        val filteredList = arrayListOf<PropertieResultItem>()

        list.forEach {
            if (elegibleForVivaReal(it) && verifyLatLon(it) && monthlyCondoFeeVerify(it)) {
                boundingBoxVivaReal(it)
                filteredList.add(it)
            }
        }
        return filteredList
    }

    private fun verifyLatLon(it: PropertieResultItem) =
        (it.address.geoLocation.location.lat.toInt() != 0 && it.address.geoLocation.location.lon.toInt() != 0)

    private fun eligibleForZap(it: PropertieResultItem) =
        ((it.pricingInfos.businessType == RENTAL && it.pricingInfos.rentalTotalPrice.toDouble() >= MIN_RENTAL_PRICE) ||
                (it.pricingInfos.businessType == SALE && it.pricingInfos.price.toDouble() >= MIN_SALE_PRICE
                        && it.usableAreas > USABLE_AREAS_MIN))

    private fun elegibleForVivaReal(it: PropertieResultItem) =
        ((it.pricingInfos.businessType == RENTAL && it.pricingInfos.rentalTotalPrice.toDouble() <= MAX_RENTAL_PRICE) ||
                (it.pricingInfos.businessType == SALE && it.pricingInfos.price.toDouble() <= MAX_SALE_PRICE))

    private fun boundingBoxZap(propertie: PropertieResultItem) {
        if ((propertie.address.geoLocation.location.lon >= MIN_LON && propertie.address.geoLocation.location.lat >= MIN_LAT)
            && (propertie.address.geoLocation.location.lon <= MAX_LON && propertie.address.geoLocation.location.lat <= MAX_LAT)
            && propertie.pricingInfos.price.isNotEmpty()
        ) {
            val newPrice =
                (PERCENTAGE_SALE * propertie.pricingInfos.price.toDouble()) - propertie.pricingInfos.price.toDouble()
            propertie.pricingInfos.price = newPrice.toString()
        }
    }

    private fun boundingBoxVivaReal(propertie: PropertieResultItem) {
        if ((propertie.address.geoLocation.location.lon >= MIN_LON && propertie.address.geoLocation.location.lat >= MIN_LAT)
            && (propertie.address.geoLocation.location.lon <= MAX_LON && propertie.address.geoLocation.location.lat <= MAX_LAT)
            && propertie.pricingInfos.rentalTotalPrice.isNotEmpty()
        ) {
            val newPrice =
                (PERCENTAGE_RENTAL * propertie.pricingInfos.rentalTotalPrice.toDouble()) + propertie.pricingInfos.rentalTotalPrice.toDouble()
            propertie.pricingInfos.rentalTotalPrice = newPrice.toString()
        }
    }

    private fun monthlyCondoFeeVerify(propertie: PropertieResultItem): Boolean {
        if (propertie.pricingInfos.monthlyCondoFee.isNotEmpty()
            && propertie.pricingInfos.rentalTotalPrice.isNotEmpty()
            && propertie.pricingInfos.businessType == RENTAL
        ) {
            val monthlyCondoFeePriceDiference =
                (PERCENTAGE_MONTHLY_CONDO_FEE * propertie.pricingInfos.rentalTotalPrice.toDouble())
            if (monthlyCondoFeePriceDiference < propertie.pricingInfos.rentalTotalPrice.toDouble()) {
                return true
            }
        }
        return false
    }
}