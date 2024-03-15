package com.example.peliculas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.database.database

val database = Firebase.database
val myRef = database.getReference("peliculas")
class AgregarPelis : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_pelis)

        var nombre = findViewById<EditText>(R.id.editNombre)
        var genero = findViewById<EditText>(R.id.editGenero)
        var anio = findViewById<EditText>(R.id.editAnio)

        val agregar = findViewById<Button>(R.id.btnAgregar)

        agregar.setOnClickListener {
            var pelicula = PeliCampos(
                nombre.text.toString(),
                genero.text.toString(),
                anio.text.toString()
            )
            myRef.push().setValue(pelicula).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Pelicula Agregada", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this, "ERROR " + task.exception, Toast.LENGTH_LONG).show()
                }
            }
        }


    }
}