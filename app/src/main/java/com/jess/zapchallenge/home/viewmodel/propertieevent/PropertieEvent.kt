package com.jess.zapchallenge.home.viewmodel.propertieevent

sealed class PropertieEvent {
    data class Loading(val status: Boolean) : PropertieEvent()
}