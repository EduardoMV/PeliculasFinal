package com.example.peliculas

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import java.util.zip.Inflater

class PeliModifier (private val context: Activity, private val arrayList: ArrayList<Pelicula>)
    : ArrayAdapter<Pelicula> (context, R.layout.item, arrayList){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater : LayoutInflater = LayoutInflater.from(context)
        var view:View = inflater.inflate(R.layout.item, null)

        view.findViewById<TextView>(R.id.nombre).text = arrayList[position].nombre.toString()
        view.findViewById<TextView>(R.id.genero).text = arrayList[position].genero.toString()
        view.findViewById<TextView>(R.id.anio).text = arrayList[position].anio.toString()

        if(arrayList[position].nombre == "Batman")
        {
            view.findViewById<ImageView>(R.id.imagenPelicula).setImageDrawable(ContextCompat.getDrawable(context, R.drawable.batman))
        }
        if(arrayList[position].nombre == "Deadpool")
        {
            view.findViewById<ImageView>(R.id.imagenPelicula).setImageDrawable(ContextCompat.getDrawable(context, R.drawable.deadpool))
        }
        if(arrayList[position].genero == "terror")
        {
            view.findViewById<ImageView>(R.id.imagenPelicula).setImageDrawable(ContextCompat.getDrawable(context, R.drawable.terror))
        }

        return view

        //return super.getView(position, convertView, parent)
    }

}