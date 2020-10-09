package com.jess.zapchallenge

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "http://grupozap-code-challenge.s3-website-us-east-1.amazonaws.com/sources/"

class PropertiesService {
    companion object {
        val SERVICE: PropertiesAPI

        init {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY

            val httpClient = OkHttpClient.Builder()
            httpClient.addNetworkInterceptor(StethoInterceptor())
            httpClient.addInterceptor(logging)

            val gson = GsonBuilder().setLenient().create()

            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient.build())
                .build()

            SERVICE = retrofit.create(
                PropertiesAPI::class.java)
        }
    }
}