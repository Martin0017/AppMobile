package com.example.login

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptionsExtension
import com.google.android.gms.fitness.Fitness
import com.google.android.gms.fitness.FitnessOptions
import com.google.android.gms.fitness.data.DataSet
import com.google.android.gms.fitness.data.DataType
import com.google.android.gms.fitness.request.DataReadRequest
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.concurrent.TimeUnit

class records_page : AppCompatActivity() {

    var actividad1: Number? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.records_page)

        val requestEmail = JsonObject()
        requestEmail.addProperty("email",SessionManager.mySessionData)
        val callEmail: Call<JsonObject> = RegistersRetrofit.api_service_databasems.searchActivity(requestEmail)
        callEmail.enqueue(object : Callback<JsonObject> {
            override fun onResponse(callEmail: Call<JsonObject>, response: Response<JsonObject>) {
                if (response.isSuccessful) {
                    val jsonRequest = response.body()
                    findViewById<TextView>(R.id.info_name2).text = jsonRequest?.get("activity")!!.asString

                    val fitnessOptions: GoogleSignInOptionsExtension = FitnessOptions.builder()
                        .addDataType(DataType.TYPE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_READ)
                        .build()


                    val readRequest = DataReadRequest.Builder()
                        .aggregate(DataType.AGGREGATE_STEP_COUNT_DELTA)
                        .bucketByTime(1, TimeUnit.DAYS)
                        .setTimeRange(LocalDate.parse(jsonRequest?.get("start_time")!!.asString, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")).toEpochDay(),
                            LocalDate.parse(jsonRequest?.get("end_time")!!.asString, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")).toEpochDay(), TimeUnit.DAYS)
                        .build()

                    Fitness.getHistoryClient(this@records_page, GoogleSignIn.getAccountForExtension(this@records_page, fitnessOptions))
                        .readData(readRequest)
                        .addOnSuccessListener { response ->
                            for (dataSet in response.buckets.flatMap { it.dataSets }) {
                                val finalGoalJsonElement: JsonElement? = jsonRequest?.get("final_goal")
                                if (finalGoalJsonElement?.isJsonPrimitive == true && finalGoalJsonElement.asJsonPrimitive.isNumber) {
                                    val finalGoalFloat: Float = finalGoalJsonElement.asFloat
                                    dumpDataSet(dataSet, finalGoalFloat)
                                }

                            }
                        }
                        .addOnFailureListener { e ->
                            Log.w("There was an error reading data from Google Fit", e)
                        }

                }
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {

            }
        })

}

    fun dumpDataSet(dataSet: DataSet, asNumber: Float) {
        Log.i("Data returned for Data type:"," ${dataSet.dataType.name}")
        for (dp in dataSet.dataPoints) {
            Log.i("Data point:"," ")
            for (field in dp.dataType.fields) {
                Log.i("\tField: ${field.name.toString()} Value:","${dp.getValue(field)}")
                findViewById<TextView>(R.id.info_steps2).text = "${dp.getValue(field)}"
                findViewById<TextView>(R.id.info_percent2).text = "${dp.getValue(field).toString().toFloat()*100/asNumber}%"

            }
        }
    }

}