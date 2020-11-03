package com.jess.zapchallenge

import com.jess.zapchallenge.home.model.PropertieResult
import retrofit2.http.GET

interface PropertiesAPI {

    companion object {
        const val SOURCE = "source-1.json"
    }

    @GET(SOURCE)
    suspend fun getProperties(): PropertieResult
}