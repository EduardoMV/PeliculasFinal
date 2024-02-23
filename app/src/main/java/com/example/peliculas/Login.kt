package com.example.peliculas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.database

class Login : AppCompatActivity() {
    private lateinit var saludoLogin: TextView
    private lateinit var btnLogin: Button
    private lateinit var btnCrearCuenta: Button
    private lateinit var btnRecuperarContra: Button
    private lateinit var auth: FirebaseAuth;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = Firebase.auth

        saludoLogin = findViewById(R.id.saludoLogin)
        btnLogin = findViewById(R.id.login)
        btnCrearCuenta = findViewById(R.id.crearCuenta)
        btnRecuperarContra = findViewById(R.id.recuperarContra)

        btnLogin.setOnClickListener {

            auth.signInWithEmailAndPassword("eduardo@eduardo.com", "eduardo2").addOnCompleteListener {
                task ->

                if(task.isSuccessful){
                    Toast.makeText(this, "Se inicio sesión correctamente", Toast.LENGTH_LONG).show()
                    startActivity(Intent(this, Menu_principal::class.java).putExtra("nombre", "Eduardo"))
                    finish()
                } else {
                    Toast.makeText(this, "Error: " + task.exception!!.message.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        }
        btnCrearCuenta.setOnClickListener {
            startActivity(Intent(this, Crear_cuenta::class.java))
        }
        btnRecuperarContra.setOnClickListener {
            startActivity(Intent(this, Cambiar_password::class.java))
        }
    }
    public override fun onStart() {
        super.onStart()

        val currentUser = auth.currentUser
        if(currentUser != null)
        {
            Toast.makeText(this, "Ya estás autenticado", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, Menu_principal::class.java).putExtra("nombre", "Eduardo"))
            finish()
        }
        else
        {
            Toast.makeText(this, "No hay usuarios autenticados", Toast.LENGTH_SHORT).show()
        }
    }
}
