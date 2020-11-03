package com.jess.zapchallenge.home.repository

import com.jess.zapchallenge.PropertiesService

class PropertiesRepository {

    suspend fun getProperties() = PropertiesService.getApi().getProperties()
}