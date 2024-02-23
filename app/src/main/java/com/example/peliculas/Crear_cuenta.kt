package com.example.peliculas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class Crear_cuenta : AppCompatActivity() {
    private lateinit var saludoCrear: TextView
    private lateinit var btnVolverLogin: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_cuenta)

        saludoCrear = findViewById(R.id.saludoCrearCuenta)
        btnVolverLogin = findViewById(R.id.backToLogin)

        btnVolverLogin.setOnClickListener {
            startActivity(Intent(this, Login::class.java))
        }
    }
}
