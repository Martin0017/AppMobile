package com.example.login

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity

class account_page : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.account_page)

        val spinnerGender = findViewById<Spinner>(R.id.spinnerGender)
        val buttonSave = findViewById<Button>(R.id.buttonSave)
        val editTextName = findViewById<EditText>(R.id.editTextName)
        val editTextBirthDate = findViewById<EditText>(R.id.editTextBirthDate)
        val editTextPhone = findViewById<EditText>(R.id.editTextPhone)
        val editTextEmail = findViewById<EditText>(R.id.editTextEmail)

        // Populating gender spinner
        val genderOptions = listOf("Masculino", "Femenino", "Otro")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, genderOptions)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerGender.adapter = adapter

        buttonSave.setOnClickListener {
            val newName = editTextName.text.toString()
            val newBirthDate = editTextBirthDate.text.toString()
            val newPhone = editTextPhone.text.toString()
            val newGender = spinnerGender.selectedItem.toString()
            val newEmail = editTextEmail.text.toString()

            // Perform save logic here, such as updating user data in a database
        }
    }
}
