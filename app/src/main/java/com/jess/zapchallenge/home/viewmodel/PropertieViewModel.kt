package com.jess.zapchallenge.home.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jess.zapchallenge.home.usecase.PropertiesUseCase
import com.jess.zapchallenge.home.viewmodel.propertieevent.PropertieEvent
import com.jess.zapchallenge.home.viewmodel.propertieinteractor.PropertieInteractor
import com.jess.zapchallenge.home.viewmodel.propertiestate.PropertieState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class PropertieViewModel : ViewModel() {
    private var _state: MutableLiveData<PropertieState> = MutableLiveData()
    private var _event: MutableLiveData<PropertieEvent> = MutableLiveData()
    private val _useCase = PropertiesUseCase()
    val state = _state
    val event = _event

    fun interpret(interactor: PropertieInteractor){
        when(interactor){
            is PropertieInteractor.ShowList -> getListProperties()
        }
    }

    private fun getListProperties(){
        viewModelScope.launch {
            _event.value = PropertieEvent.Loading(true)
            try {
                val propertiesList = withContext(Dispatchers.IO){
                    _useCase.getProperties()
                }
                _state.value = PropertieState.PropertiesListSuccess(propertiesList)
                _event.value = PropertieEvent.Loading(false)
            }catch (ex: Exception){
                errorApi(ex.message.toString())
            }
        }
    }

    private fun errorApi(message: String) {
        _state.value = PropertieState.PropertiesListError(message)
    }
}