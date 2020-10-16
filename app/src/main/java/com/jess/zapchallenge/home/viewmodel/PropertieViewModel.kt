package com.jess.zapchallenge.home.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jess.zapchallenge.home.model.PropertieResultItem
import com.jess.zapchallenge.home.repository.PropertiesRepository
import com.jess.zapchallenge.home.usecase.PropertieUseCase
import com.jess.zapchallenge.home.viewmodel.propertieevent.PropertieEvent
import com.jess.zapchallenge.home.viewmodel.propertieinteractor.PropertieInteractor
import com.jess.zapchallenge.home.viewmodel.propertiestate.PropertieState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PropertieViewModel(
    private val ioDispatcher: CoroutineDispatcher,
    private val repository: PropertiesRepository
) : ViewModel() {
    private var state: MutableLiveData<PropertieState> = MutableLiveData()
    private var event: MutableLiveData<PropertieEvent> = MutableLiveData()
    private val useCase = PropertieUseCase()
    val viewState = state
    val viewEvent = event

    fun interpret(interactor: PropertieInteractor) {
        when (interactor) {
            is PropertieInteractor.GetList -> getListProperties()
            is PropertieInteractor.PropertieDetail -> getDetail(interactor.propertie)
        }
    }

    private fun getListProperties() {
        viewModelScope.launch {
            event.value = PropertieEvent.Loading(true)
            try {
                val listPropertiesResult = withContext(ioDispatcher) {
                    repository.getProperties()
                }
                state.value = PropertieState.PropertiesListSuccess(listPropertiesResult)
                event.value = PropertieEvent.Loading(false)
            } catch (ex: Exception) {
                state.value =
                    PropertieState.PropertiesListError("Ops! Parece que tivemos algum problema =/\nTente novamente!")
            }
        }
    }

    fun listFilter(
        tabPosition: Int,
        list: ArrayList<PropertieResultItem>
    ): ArrayList<PropertieResultItem> {

        var filteredList = arrayListOf<PropertieResultItem>()
        when (tabPosition) {
            0 -> filteredList = useCase.listGroupZap(list)
            1 -> filteredList = useCase.listGroupVivaReal(list)
        }
        return filteredList
    }

    private fun getDetail(propertie: PropertieResultItem) {
        event.value = PropertieEvent.ShowPropertieDetail(propertie)
    }
}