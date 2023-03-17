package com.example.cacsapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class ECrudEntrenador : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ecrud_entrenador)

        val id = findViewById<EditText>(R.id.input_id)
        val nombre = findViewById<EditText>(R.id.input_nombre)
        val descripcion = findViewById<EditText>(R.id.input_descripcion)

        val botonBuscarBDD = findViewById<Button>(R.id.btn_buscar_bdd).apply{
            setOnClickListener{

                val entrenador = EBaseDeDatos.tablaEntrenador!!.consultarEntrenadorPorId(id.text.toString().toInt())
                id.setText(entrenador.id.toString())
                nombre.setText(entrenador.nombre)
                descripcion.setText(entrenador.descripcion)
            }
        }

        val botonCrearBDD : Button = findViewById(R.id.btn_crear_bdd)
        botonCrearBDD.setOnClickListener{
            val seCreo = EBaseDeDatos.tablaEntrenador!!.crearEntrenador(nombre.text.toString(), descripcion.text.toString())
            if(!seCreo)
                println("No se creo")
        }

        val botonActualizarBDD : Button = findViewById(R.id.btn_actualizar_bdd)
        botonActualizarBDD.setOnClickListener{
            EBaseDeDatos.tablaEntrenador!!.actualizarEntrenadorFormulario(nombre.text.toString(), descripcion.toString(), id.text.toString().toInt())
        }

        val botonEliminarBDD : Button = findViewById(R.id.btn_eliminar_bdd)
        botonEliminarBDD.setOnClickListener{
            EBaseDeDatos.tablaEntrenador!!.eliminarEntrenadorFormulario(id.text.toString().toInt())
        }
    }
}