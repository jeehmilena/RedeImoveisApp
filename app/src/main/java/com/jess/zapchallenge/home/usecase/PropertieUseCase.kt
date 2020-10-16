package com.jess.zapchallenge.home.usecase

import com.jess.zapchallenge.Constants.MAX_RENTAL_PRICE
import com.jess.zapchallenge.Constants.MAX_SALE_PRICE
import com.jess.zapchallenge.Constants.MIN_RENTAL_PRICE
import com.jess.zapchallenge.Constants.MIN_SALE_PRICE
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
        if ((propertie.address.geoLocation.location.lon >= -46.693419 && propertie.address.geoLocation.location.lat >= -23.568704)
            && (propertie.address.geoLocation.location.lon <= -46.641146 && propertie.address.geoLocation.location.lat <= -23.546686)
            && propertie.pricingInfos.price.isNotEmpty()
        ) {
            val newPrice =
                (0.10 * propertie.pricingInfos.price.toDouble()) - propertie.pricingInfos.price.toDouble()
            propertie.pricingInfos.price = newPrice.toString()
        }
    }

    private fun boundingBoxVivaReal(propertie: PropertieResultItem) {
        if ((propertie.address.geoLocation.location.lon >= -46.693419 && propertie.address.geoLocation.location.lat >= -23.568704)
            && (propertie.address.geoLocation.location.lon <= -46.641146 && propertie.address.geoLocation.location.lat <= -23.546686)
            && propertie.pricingInfos.rentalTotalPrice.isNotEmpty()
        ) {
            val newPrice =
                (0.50 * propertie.pricingInfos.rentalTotalPrice.toDouble()) + propertie.pricingInfos.rentalTotalPrice.toDouble()
            propertie.pricingInfos.rentalTotalPrice = newPrice.toString()
        }
    }

    private fun monthlyCondoFeeVerify(propertie: PropertieResultItem): Boolean {
        if (propertie.pricingInfos.monthlyCondoFee.isNotEmpty()
            && propertie.pricingInfos.rentalTotalPrice.isNotEmpty()
            && propertie.pricingInfos.businessType == RENTAL
        ) {
            val monthlyCondoFeePriceDiference =
                (0.30 * propertie.pricingInfos.rentalTotalPrice.toDouble())
            if (monthlyCondoFeePriceDiference < propertie.pricingInfos.rentalTotalPrice.toDouble()) {
                return true
            }
        }
        return false
    }
}