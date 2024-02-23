package com.example.peliculas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class Cambiar_password : AppCompatActivity() {
    private lateinit var saludoCambiar: TextView
    private lateinit var btnVolverLogin: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cambiar_password)

        saludoCambiar = findViewById(R.id.saludoPassword)
        btnVolverLogin = findViewById(R.id.backToLoginPassword)

        btnVolverLogin.setOnClickListener {
            startActivity(Intent(this, Login::class.java))
        }
    }
}
