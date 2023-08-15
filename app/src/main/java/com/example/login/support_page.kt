package com.example.login

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class support_page : AppCompatActivity() {

    private lateinit var editTextSubject: EditText
    private lateinit var editTextMessage: EditText
    private lateinit var buttonSend: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.support_page)

        editTextSubject = findViewById(R.id.editTextSubject)
        editTextMessage = findViewById(R.id.editTextMessage)
        buttonSend = findViewById(R.id.buttonSend)

        buttonSend.setOnClickListener {
            val subject = editTextSubject.text.toString()
            val message = editTextMessage.text.toString()

            if (subject.isNotEmpty() && message.isNotEmpty()) {
                // Aquí podrías implementar la lógica para enviar el mensaje de soporte, ya sea por correo electrónico, API, etc.
                // Por ahora, mostraremos un mensaje de éxito ficticio.
                Toast.makeText(this, "Mensaje enviado con éxito", Toast.LENGTH_SHORT).show()
                editTextSubject.text.clear()
                editTextMessage.text.clear()
            } else {
                Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
