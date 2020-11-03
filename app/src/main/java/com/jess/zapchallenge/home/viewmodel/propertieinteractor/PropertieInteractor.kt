package com.jess.zapchallenge.home.viewmodel.propertieinteractor

import com.jess.zapchallenge.home.model.PropertieResultItem

sealed class PropertieInteractor {
    object GetList : PropertieInteractor()
    data class PropertieDetail(val propertie: PropertieResultItem) : PropertieInteractor()
}