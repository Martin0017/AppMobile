package com.example.login

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object DatabasemsRetrofit {
    private const val BASE_URL = "http://137.184.7.251:3001/api_db/"

    private val databasems_retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api_service_databasems: DatabasemsService = databasems_retrofit.create(DatabasemsService::class.java)

}