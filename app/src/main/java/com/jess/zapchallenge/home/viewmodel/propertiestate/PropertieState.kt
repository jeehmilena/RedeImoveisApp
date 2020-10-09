package com.jess.zapchallenge.home.viewmodel.propertiestate

import com.jess.zapchallenge.home.model.PropertieResultItem

sealed class PropertieState {
    data class PropertiesListSuccess(val properties: List<PropertieResultItem>) : PropertieState()
    data class PropertiesListError(val messageError: String): PropertieState()
}