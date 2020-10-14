package com.jess.zapchallenge.home.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jess.zapchallenge.Constants.MAX_RENTAL_PRICE
import com.jess.zapchallenge.Constants.MIN_RENTAL_PRICE
import com.jess.zapchallenge.Constants.MIN_SALE_PRICE
import com.jess.zapchallenge.Constants.RENTAL
import com.jess.zapchallenge.Constants.SALE
import com.jess.zapchallenge.home.model.PropertieResultItem
import com.jess.zapchallenge.home.repository.PropertiesRepository
import com.jess.zapchallenge.home.viewmodel.propertieevent.PropertieEvent
import com.jess.zapchallenge.home.viewmodel.propertieinteractor.PropertieInteractor
import com.jess.zapchallenge.home.viewmodel.propertiestate.PropertieState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PropertieViewModel : ViewModel() {
    private var _state: MutableLiveData<PropertieState> = MutableLiveData()
    private var _event: MutableLiveData<PropertieEvent> = MutableLiveData()
    private val _repository = PropertiesRepository()
    val state = _state
    val event = _event
    private var _listPropertiesResult = ArrayList<PropertieResultItem>()

    fun interpret(interactor: PropertieInteractor) {
        when (interactor) {
            is PropertieInteractor.GetList -> getListProperties()
        }
    }

    private fun getListProperties() {
        viewModelScope.launch {
            _event.value = PropertieEvent.Loading(true)
            try {
                _listPropertiesResult = withContext(Dispatchers.IO) {
                    _repository.getProperties()
                }
                _state.value = PropertieState.PropertiesListSuccess(_listPropertiesResult)
                _event.value = PropertieEvent.Loading(false)
            } catch (ex: Exception) {
                _state.value =
                    PropertieState.PropertiesListError("Ops! Parece que tivemos algum problema =/\nTente novamente!")
            }
        }
    }

     fun listFilter(
        tabPosition: Int
    ): ArrayList<PropertieResultItem> {

        var filteredList = arrayListOf<PropertieResultItem>()
        when (tabPosition) {
            0 -> filteredList = listGroupZap(_listPropertiesResult)
            1 -> filteredList = listGroupVivaReal(_listPropertiesResult)
        }

        return filteredList
    }

    private fun listGroupZap(list: ArrayList<PropertieResultItem>): ArrayList<PropertieResultItem> {
        val filteredList = arrayListOf<PropertieResultItem>()

        list.forEach {
            if ((it.pricingInfos.businessType == RENTAL && it.pricingInfos.price >= MIN_RENTAL_PRICE) ||
                (it.pricingInfos.businessType == SALE && it.pricingInfos.price >= MIN_SALE_PRICE)
            ) {
                filteredList.add(it)
            }
        }
        return filteredList
    }

    private fun listGroupVivaReal(list: ArrayList<PropertieResultItem>): ArrayList<PropertieResultItem> {
        val filteredList = arrayListOf<PropertieResultItem>()

        list.forEach {
            if ((it.pricingInfos.businessType == RENTAL && it.pricingInfos.price <= MAX_RENTAL_PRICE) ||
                (it.pricingInfos.businessType == SALE && it.pricingInfos.price <= MAX_RENTAL_PRICE)
            ) {
                filteredList.add(it)
            }
        }
        return filteredList
    }
}