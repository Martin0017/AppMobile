package com.example.login

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class rewards_page :  AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.rewards_page)

        val btnRegresar = findViewById<Button>(R.id.btnVolver)
        btnRegresar.setOnClickListener {
            val intent = Intent(this, points_page::class.java)
            startActivity(intent)
        }
    }
}