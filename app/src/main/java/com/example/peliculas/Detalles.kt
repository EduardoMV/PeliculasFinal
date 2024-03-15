package com.example.peliculas

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.Firebase
import com.google.firebase.database.database

class Detalles : AppCompatActivity() {

    val database = Firebase.database
    val myRef = database.getReference(("peliculas"))
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalles)

        var nombre = findViewById<EditText>(R.id.nombrec)
        var genero = findViewById<EditText>(R.id.generoc)
        var anio = findViewById<EditText>(R.id.anioc)

        var imagen = findViewById<ImageView>(R.id.imagenDetalles)

        val editar = findViewById<Button>(R.id.editar)
        val eliminar = findViewById<Button>(R.id.eliminar)
        val parametros = intent.extras

        nombre.setText(parametros?.getCharSequence("nombre").toString())
        genero.setText(parametros?.getCharSequence("genero").toString())
        anio.setText(parametros?.getCharSequence("anio").toString())

        if (parametros?.getCharSequence("genero").toString() == "accion") {
            imagen.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.batman))
        } else if (parametros?.getCharSequence("genero").toString() == "comedia") {
            imagen.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.deadpool))
        }
        editar.setOnClickListener {
            var pelicula =
                PeliCampos(nombre.text.toString(), genero.text.toString(), anio.text.toString())

            myRef.child(parametros?.getCharSequence("id").toString()).setValue(pelicula)
                .addOnCompleteListener { task ->

                    if (task.isSuccessful) {
                        Toast.makeText(this, "Pelicula Editada ", Toast.LENGTH_LONG).show()
                        if (genero.text.toString() == "accion") {
                            imagen.setImageDrawable(
                                ContextCompat.getDrawable(
                                    this,
                                    R.drawable.batman
                                )
                            )
                        } else if (genero.text.toString() == "comedia") {
                            imagen.setImageDrawable(
                                ContextCompat.getDrawable(
                                    this,
                                    R.drawable.deadpool
                                )
                            )
                        }

                    } else {
                        Toast.makeText(
                            this,
                            "Error: " + task.exception!!.message.toString(),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }
        eliminar.setOnClickListener {
            val builder: AlertDialog.Builder = MaterialAlertDialogBuilder(this)

            builder.setMessage("¿Mami borra las fotos?")
                .setPositiveButton("Si, continuar") { dialog, id ->
                    myRef.child(parametros?.getCharSequence("id").toString()).removeValue()
                        .addOnCompleteListener() { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(this, "Pelicula Eliminada ", Toast.LENGTH_LONG)
                                    .show()
                                finish()
                            } else {
                                Toast.makeText(
                                    this,
                                    "Error: " + task.exception!!.message.toString(),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                }

                .setNegativeButton("No, no borrar") { dialog, id ->
                    Toast.makeText(this, "No se borro", Toast.LENGTH_LONG).show()
                }
            val alertDialog: AlertDialog = builder.create()
            alertDialog.show()

        }
    }
}