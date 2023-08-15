package com.example.login

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RegistersRetrofit {
    private const val BASE_URL = "http://137.184.7.251:3004/"

    private val register_retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api_service_databasems: RegistersService = register_retrofit.create(RegistersService::class.java)
}