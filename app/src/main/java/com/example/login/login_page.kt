package com.example.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.fitness.FitnessOptions
import com.google.android.gms.fitness.data.DataType
import com.google.gson.Gson
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class login_page : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {

        val GOOGLE_FIT_PERMISSIONS_REQUEST_CODE = 101
        val MY_PERMISSIONS_REQUEST_ACTIVITY_RECOGNITION = 1

        val fitnessOptions = FitnessOptions.builder()
            .addDataType(DataType.TYPE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_READ)
            .build()

        if (!GoogleSignIn.hasPermissions(GoogleSignIn.getLastSignedInAccount(this), fitnessOptions)) {
            GoogleSignIn.requestPermissions(
                this,
                GOOGLE_FIT_PERMISSIONS_REQUEST_CODE,
                GoogleSignIn.getLastSignedInAccount(this),
                fitnessOptions
            )
        }

        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACTIVITY_RECOGNITION)
            != PackageManager.PERMISSION_GRANTED) {
            Log.i("Ojala","Haber")
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.ACTIVITY_RECOGNITION),
                MY_PERMISSIONS_REQUEST_ACTIVITY_RECOGNITION
            )
        }


        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_page)
        val btnIrAPestana = findViewById<Button>(R.id.btnI)
        val emailEditText = findViewById<EditText>(R.id.e_mail)
        val passwordEditText = findViewById<EditText>(R.id.password)

        btnIrAPestana.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()
            val userCredentials = UserCredentials(email, password)
            Log.d("ddd",userCredentials.contrasena_user)
            val call: Call<Boolean> = OauthRetrofit.apiService.loginWithBody(userCredentials)
            Log.d("Usuario", "Respuesta vacía del servidodvvr")

            call.enqueue(object : Callback<Boolean> {
                override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                    if (response.isSuccessful) {
                        val result: Boolean? = response.body()
                        if(result != null && result){
                            SessionManager.mySessionData = email
                            val intent = Intent(this@login_page, menu_page::class.java)
                            startActivity(intent)
                        }else{
                            Toast.makeText(this@login_page, "Credenciales incorrectas", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Log.d("Usuario", "Error en onResponse: ${response.code()} - ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<Boolean>, t: Throwable) {
                    Log.d("Usuario", "Error en onFailure: ${t.message}")
                }
            })

        }
    }


}

