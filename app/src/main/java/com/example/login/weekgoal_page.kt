package com.example.login

import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Email
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Calendar

class weekgoal_page: AppCompatActivity() {

    var actividad1: JsonObject? = null
    var actividad2: JsonObject? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.weekgoal_page)

        val call: Call<JsonArray> = DatabasemsRetrofit.api_service_databasems.getActivities()
        call.enqueue(object : Callback<JsonArray> {
            override fun onResponse(call: Call<JsonArray>, response: Response<JsonArray>) {
                if (response.isSuccessful) {
                    val jsonArray = response.body() // Obtener el JsonArray

                    if (jsonArray != null) {
                        val gson = Gson()

                            actividad1 = jsonArray.get(0).asJsonObject
                            actividad2 = jsonArray.get(1).asJsonObject

                        var acti1 = actividad1
                        var acti2 = actividad2

                        val textActividad = findViewById<TextView>(R.id.weekgoalactivity)
                        textActividad.text = acti1?.get("descripcion_acti")!!.asString
                        val textActividad2 = findViewById<TextView>(R.id.weekgoalactivity2)
                        textActividad2.text = acti2?.get("descripcion_acti")!!.asString

                    } else {
                        // Manejar caso donde el JsonArray es nulo
                    }
                } else {
                    // Manejar el error de respuesta
                }
            }

            override fun onFailure(call: Call<JsonArray>, t: Throwable) {
                // Manejar el error de la llamada
            }
        })


        val btnIniciar = findViewById<Button>(R.id.btnMapa)
        btnIniciar.setOnClickListener {
            val requestEmail = JsonObject()
            requestEmail.addProperty("email",SessionManager.mySessionData)
            val callEmail: Call<JsonObject> = RegistersRetrofit.api_service_databasems.searchActivity(requestEmail)
            callEmail.enqueue(object : Callback<JsonObject> {
                override fun onResponse(callEmail: Call<JsonObject>, response: Response<JsonObject>) {
                    if (response.isSuccessful) {
                        val jsonRequest = response.body()
                        val isInit = jsonRequest?.get("in_process")?.asBoolean
                        if (isInit == true){
                            Toast.makeText(this@weekgoal_page, "Ya has iniciado esta actividad", Toast.LENGTH_SHORT).show()
                        }else{
                            val fechaHoraActual: LocalDateTime = LocalDateTime.now()
                            val start: String = fechaHoraActual.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))

                            val fechaHoraDespuesDe7Dias: LocalDateTime = fechaHoraActual.plus(7, ChronoUnit.DAYS)
                            val end: String = fechaHoraDespuesDe7Dias.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))


                            val requestData = JsonObject()
                            requestData.addProperty("name", actividad1?.get("nombre_acti")!!.asString)
                            requestData.addProperty("email",SessionManager.mySessionData)
                            requestData.addProperty("activity",actividad1?.get("descripcion_acti")!!.asString)
                            requestData.addProperty("steeps",0)
                            requestData.addProperty("start_time",start)
                            requestData.addProperty("end_time",end)
                            requestData.addProperty("final_goal",100000)
                            requestData.addProperty("in_process",true)


                            val call: Call<JsonObject> = RegistersRetrofit.api_service_databasems.setActivity(requestData)
                            call.enqueue(object : Callback<JsonObject> {
                                override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                                    if (response.isSuccessful) {
                                        val intent = Intent(this@weekgoal_page, mapa_page::class.java)
                                        intent.putExtra("json_data", actividad1.toString())
                                        startActivity(intent)
                                    } else {
                                    }
                                }

                                override fun onFailure(call: Call<JsonObject>, t: Throwable) {

                                }
                            })
                        }
                    }else{

                    }}

                override fun onFailure(call: Call<JsonObject>, t: Throwable) {

                    val fechaHoraActual: LocalDateTime = LocalDateTime.now()
                    val start: String = fechaHoraActual.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))

                    val fechaHoraDespuesDe7Dias: LocalDateTime = fechaHoraActual.plus(7, ChronoUnit.DAYS)
                    val end: String = fechaHoraDespuesDe7Dias.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))


                    val requestData = JsonObject()
                    requestData.addProperty("name", actividad1?.get("nombre_acti")!!.asString)
                    requestData.addProperty("email",SessionManager.mySessionData)
                    requestData.addProperty("activity",actividad1?.get("descripcion_acti")!!.asString)
                    requestData.addProperty("steeps",0)
                    requestData.addProperty("start_time",start)
                    requestData.addProperty("end_time",end)
                    requestData.addProperty("final_goal",100000)
                    requestData.addProperty("in_process",true)


                    val call: Call<JsonObject> = RegistersRetrofit.api_service_databasems.setActivity(requestData)
                    call.enqueue(object : Callback<JsonObject> {
                        override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                            if (response.isSuccessful) {
                                val intent = Intent(this@weekgoal_page, mapa_page::class.java)
                                intent.putExtra("json_data", actividad1.toString())
                                startActivity(intent)
                            } else {
                            }
                        }

                        override fun onFailure(call: Call<JsonObject>, t: Throwable) {

                        }
                    })

                    // Obtén el mensaje de error de la excepción
                    val errorMessage = t.message

                    // Imprime el mensaje de error
                    Log.d("Error:"," $errorMessage")
                }
            })

        }

        val btnIniciar2 = findViewById<Button>(R.id.btnMapa2)
        btnIniciar2.setOnClickListener {

            val requestEmail = JsonObject()
            requestEmail.addProperty("email",SessionManager.mySessionData)
            val callEmail: Call<JsonObject> = RegistersRetrofit.api_service_databasems.searchActivity(requestEmail)
            callEmail.enqueue(object : Callback<JsonObject> {
                override fun onResponse(callEmail: Call<JsonObject>, response: Response<JsonObject>) {
                    if (response.isSuccessful) {
                        val jsonRequest = response.body()
                        val isInit = jsonRequest?.get("in_process")?.asBoolean
                        if (isInit == true){
                            Toast.makeText(this@weekgoal_page, "Ya has iniciado esta actividad", Toast.LENGTH_SHORT).show()
                        }else{
                            val fechaHoraActual: LocalDateTime = LocalDateTime.now()
                            val start: String = fechaHoraActual.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))

                            val fechaHoraDespuesDe7Dias: LocalDateTime = fechaHoraActual.plus(7, ChronoUnit.DAYS)
                            val end: String = fechaHoraDespuesDe7Dias.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))


                            val requestData = JsonObject()
                            requestData.addProperty("name", actividad2?.get("nombre_acti")!!.asString)
                            requestData.addProperty("email",SessionManager.mySessionData)
                            requestData.addProperty("activity",actividad2?.get("descripcion_acti")!!.asString)
                            requestData.addProperty("steeps",0)
                            requestData.addProperty("start_time",start)
                            requestData.addProperty("end_time",end)
                            requestData.addProperty("final_goal",80000)
                            requestData.addProperty("in_process",true)


                            val call: Call<JsonObject> = RegistersRetrofit.api_service_databasems.setActivity(requestData)
                            call.enqueue(object : Callback<JsonObject> {
                                override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                                    if (response.isSuccessful) {
                                        val intent = Intent(this@weekgoal_page, mapa_page::class.java)
                                        intent.putExtra("json_data", actividad2.toString())
                                        startActivity(intent)
                                    } else {
                                    }
                                }

                                override fun onFailure(call: Call<JsonObject>, t: Throwable) {

                                }
                            })
                        }
                    }else{

                    }}

                override fun onFailure(call: Call<JsonObject>, t: Throwable) {

                    val fechaHoraActual: LocalDateTime = LocalDateTime.now()
                    val start: String = fechaHoraActual.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))

                    val fechaHoraDespuesDe7Dias: LocalDateTime = fechaHoraActual.plus(7, ChronoUnit.DAYS)
                    val end: String = fechaHoraDespuesDe7Dias.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))


                    val requestData = JsonObject()
                    requestData.addProperty("name", actividad2?.get("nombre_acti")!!.asString)
                    requestData.addProperty("email",SessionManager.mySessionData)
                    requestData.addProperty("activity",actividad2?.get("descripcion_acti")!!.asString)
                    requestData.addProperty("steeps",0)
                    requestData.addProperty("start_time",start)
                    requestData.addProperty("end_time",end)
                    requestData.addProperty("final_goal",80000)
                    requestData.addProperty("in_process",true)


                    val call: Call<JsonObject> = RegistersRetrofit.api_service_databasems.setActivity(requestData)
                    call.enqueue(object : Callback<JsonObject> {
                        override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                            if (response.isSuccessful) {
                                val intent = Intent(this@weekgoal_page, mapa_page::class.java)
                                intent.putExtra("json_data", actividad2.toString())
                                startActivity(intent)
                            } else {
                            }
                        }

                        override fun onFailure(call: Call<JsonObject>, t: Throwable) {

                        }
                    })

                    // Obtén el mensaje de error de la excepción
                    val errorMessage = t.message

                    // Imprime el mensaje de error
                    Log.d("Error:"," $errorMessage")
                }
            })


        }}
}