package com.jess.zapchallenge.home.usecase

import com.jess.zapchallenge.home.repository.PropertiesRepository

class PropertiesUseCase {
    private val repository = PropertiesRepository()

    suspend fun getProperties() = repository.getProperties()
}