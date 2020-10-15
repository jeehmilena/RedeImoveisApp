package com.jess.zapchallenge.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jess.zapchallenge.home.repository.PropertiesRepository
import kotlinx.coroutines.CoroutineDispatcher

class PropertieViewModelFactory(
    private val ioDispatcher: CoroutineDispatcher,
    private val repository: PropertiesRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(
            CoroutineDispatcher::class.java,
            PropertiesRepository::class.java
        ).newInstance(ioDispatcher, repository)
    }
}