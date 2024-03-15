package com.example.peliculas

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.ListView
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import kotlinx.coroutines.awaitAll

class Menu_principal : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth;
    val database = Firebase.database
    val myRef = database.getReference("peliculas")
    lateinit var peliculas:ArrayList<Pelicula>

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_principal)

        auth = Firebase.auth
        val menuHost: MenuHost = this
        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)



        menuHost.addMenuProvider(object : MenuProvider {

            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.salir -> {
                        auth.signOut()
                        startActivity(Intent(this@Menu_principal, Login::class.java))
                        finish()
                        true
                    }

                    R.id.perfil -> {
                        true
                    }

                    else -> false
                }
            }

        })

        myRef.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                peliculas = ArrayList<Pelicula>()
                val value = snapshot.value
                Log.d(TAG, "Value is: " + value)

                snapshot.children.forEach { hijo ->

                    var pelicula: Pelicula = Pelicula(
                        hijo.child("nombre").value.toString(),
                        hijo.child("genero").value.toString(),
                        hijo.child("anio").value.toString(),
                        hijo.key.toString()
                    )
                    peliculas.add(pelicula)
                }
                llenaLista()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(TAG, "Failed to read value.", error.toException())
            }

        })

        val lista = findViewById<ListView>(R.id.lista)

        lista.setOnItemClickListener { parent, view, position, id ->
            startActivity(
                Intent(this, Detalles::class.java)
                    .putExtra("nombre", peliculas[position].nombre)
                    .putExtra("genero", peliculas[position].genero)
                    .putExtra("anio", peliculas[position].anio)
                    .putExtra("id", peliculas[position].id)
            )
        }

        val btnagregar = findViewById<FloatingActionButton>(R.id.botonAgregar)

        btnagregar.setOnClickListener {
            startActivity(Intent(this, AgregarPelis::class.java))
        }

    }


    fun llenaLista ()
    {
        val adaptador = PeliModifier(this, peliculas)
        var lista = findViewById<ListView>(R.id.lista)
        lista.adapter = adaptador
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {

        if(keyCode == KeyEvent.KEYCODE_BACK)
        {
            finish()
        }
        return super.onKeyDown(keyCode, event)
    }

    public override fun onStart() {
        super.onStart()

        val currentUser = auth.currentUser
        if(currentUser != null)
        {
            Toast.makeText(this, "Autetificación exitosa", Toast.LENGTH_SHORT).show()
        }
        else{
            finish()
        }
    }
}

