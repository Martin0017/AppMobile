package com.example.login

import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface RegistersService {
    @POST("useractivity")
    fun setActivity(@Body data: JsonObject): Call<JsonObject>

    @POST("useractivity/search")
    fun searchActivity(@Body data: JsonObject): Call<JsonObject>

}