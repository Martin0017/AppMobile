package com.example.login

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface DatabasemsService {
    @POST("user/search")
    fun searchUserByMail(@Body data: JsonObject): Call<JsonObject>

    @GET("activity")
    fun getActivities(): Call<JsonArray>

}