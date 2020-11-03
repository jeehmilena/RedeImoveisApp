package com.jess.zapchallenge

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PropertiesService {
    companion object {
        var BASE_URL = "http://grupozap-code-challenge.s3-website-us-east-1.amazonaws.com/sources/"
        private val gson = GsonBuilder().setLenient().create()
        private val httpClient = OkHttpClient.Builder()

        init {
            val httpClient = OkHttpClient.Builder()
            if (BuildConfig.DEBUG) {
                val logging = HttpLoggingInterceptor()
                logging.level = HttpLoggingInterceptor.Level.BODY
                httpClient.addInterceptor(logging)
            }
        }

        fun getApi(): PropertiesAPI {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient.build())
                .build()
                .create(PropertiesAPI::class.java)
        }

        fun setBaseUrl(baseUrl: String) {
            BASE_URL = baseUrl
        }
    }
}
