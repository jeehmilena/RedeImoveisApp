package com.jess.zapchallenge

import com.jess.zapchallenge.home.model.PropertieResult
import retrofit2.http.GET

interface PropertiesAPI {

    @GET("source-1.json")
    suspend fun getProperties(): PropertieResult
}