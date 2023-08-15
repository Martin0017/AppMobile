package com.example.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import com.example.login.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptionsExtension
import com.google.android.gms.fitness.Fitness
import com.google.android.gms.fitness.FitnessOptions
import com.google.android.gms.fitness.data.DataPoint
import com.google.android.gms.fitness.data.DataSet
import com.google.android.gms.fitness.data.DataType
import com.google.android.gms.fitness.request.DataReadRequest
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.gson.JsonObject
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Calendar
import java.util.concurrent.TimeUnit

class mapa_page : AppCompatActivity(), OnMapReadyCallback{

    private lateinit var map:GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mapa_page)
        createFragment()

        val jsonString = intent.getStringExtra("json_data")

        val fitnessOptions: GoogleSignInOptionsExtension = FitnessOptions.builder()
            .addDataType(DataType.TYPE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_READ)
            .build()

/*
        val readRequest = DataReadRequest.Builder()
            .aggregate(DataType.AGGREGATE_STEP_COUNT_DELTA)
            .bucketByTime(1, TimeUnit.DAYS)
            .setTimeRange(startTime.toEpochSecond(), endTime.toEpochSecond(), TimeUnit.SECONDS)
            .build()

        Fitness.getHistoryClient(this, GoogleSignIn.getAccountForExtension(this, fitnessOptions))
            .readData(readRequest)
            .addOnSuccessListener { response ->
                for (dataSet in response.buckets.flatMap { it.dataSets }) {
                    dumpDataSet(dataSet)
                }
            }
            .addOnFailureListener { e ->
                Log.w("There was an error reading data from Google Fit", e)
            }*/

        if (jsonString != null) {
            val jsonObject = JSONObject(jsonString)

            findViewById<TextView>(R.id.text2).text = "${jsonObject.get("tiempo_acti")}"
            findViewById<TextView>(R.id.modeText).text = "${jsonObject.get("nombre_acti")}"
            findViewById<TextView>(R.id.textD).text= "${jsonObject.get("descripcion_acti")}"
            findViewById<TextView>(R.id.text4).text= "${jsonObject.get("cal_quemadas_acti")}"
            findViewById<TextView>(R.id.text6).text= "${jsonObject.get("puntos_ot_acti")}"
        }

    }

    fun dumpDataSet(dataSet: DataSet) {
        Log.i("Data returned for Data type:"," ${dataSet.dataType.name}")
        for (dp in dataSet.dataPoints) {
            Log.i("Data point:"," ")
            Log.i("\tType:"," ${dp.dataType.name}")
            Log.i("\tStart:"," ${dp.getStartTimeString()}")
            Log.i("\tEnd: ","${dp.getEndTimeString()}")
            for (field in dp.dataType.fields) {
                Log.i("\tField: ${field.name.toString()} Value:","${dp.getValue(field)}")
            }
        }
    }

    fun DataPoint.getStartTimeString() = Instant.ofEpochSecond(this.getStartTime(TimeUnit.SECONDS))
        .atZone(ZoneId.systemDefault())
        .toLocalDateTime().toString()

    fun DataPoint.getEndTimeString() = Instant.ofEpochSecond(this.getEndTime(TimeUnit.SECONDS))
        .atZone(ZoneId.systemDefault())
        .toLocalDateTime().toString()



    private fun createFragment(){
        val mapFragment:SupportMapFragment= supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        createMarker()
    }

    private fun createMarker() {
        val coordinate = LatLng(-0.314975, -78.442196)
        val marker = MarkerOptions().position(coordinate).title("ESPE")
        map.addMarker(marker)

        val cameraPosition = CameraPosition.Builder()
            .target(coordinate)
            .zoom(16f) // Ajusta el nivel de zoom deseado
            .build()

        map.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
    }

}