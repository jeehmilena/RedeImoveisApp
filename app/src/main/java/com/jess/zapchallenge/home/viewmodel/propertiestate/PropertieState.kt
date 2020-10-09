package com.jess.zapchallenge.home.viewmodel.propertiestate

sealed class PropertieState {
    data class PropertiesListSuccess(val properties: List<PropertieResultItem>) : PropertieState()
    data class PropertiesListError(val messageError: String): PropertieState()
}