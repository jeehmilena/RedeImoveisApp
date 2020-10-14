package com.jess.zapchallenge.home.viewmodel.propertieevent

import com.jess.zapchallenge.home.model.PropertieResultItem

sealed class PropertieEvent {
    data class Loading(val status: Boolean) : PropertieEvent()
    data class ShowPropertieDetail(val propertie: PropertieResultItem): PropertieEvent()
}