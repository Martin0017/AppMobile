package com.example.login

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class points_page: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.points_page)

        val btnIrARecompensa = findViewById<Button>(R.id.bntCanjea) // Corregido el ID del botón
        btnIrARecompensa.setOnClickListener {
            val intent = Intent(this, rewards_page::class.java) // Corregido el nombre de la clase de destino
            startActivity(intent)
        }

    }
}
