package com.example.cacsapplication

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcel
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AlertDialog

class BListView : AppCompatActivity() {
    val arreglo = BBaseDatosMemoria.arregloBEntrenador
    var idItemSeleccionado = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blist_view)
        val listView = findViewById<ListView>(R.id.lv_list_view)
        val adaptador = ArrayAdapter<BEntrenador>(this, android.R.layout.simple_list_item_1, arreglo)
        listView.adapter = adaptador
        adaptador.notifyDataSetChanged()

        val botonAnadirListView = findViewById<Button>(R.id.btn_anadir_list_view).also{
            it.setOnClickListener{
                anadirEntrenador(adaptador)
            }
        }

        registerForContextMenu(listView)
    }

    fun anadirEntrenador(
        adaptador: ArrayAdapter<BEntrenador>
    ){
        arreglo.add(
            BEntrenador(
                1,
                "Adrian",
                "Descripcion"
            )
        )

        adaptador.notifyDataSetChanged()
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)

        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val id = info.position
        idItemSeleccionado = id
        println("Se creo el menu con id = ${id}")
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        println("Se selecciono un item")
        return when (item.itemId){
            R.id.mi_editar -> {
                "${idItemSeleccionado}"
                return true
            }

            R.id.mi_eliminar -> {
                abrirDialogo()
                "${idItemSeleccionado}"
                return true
            }

            else -> super.onContextItemSelected(item)
        }
    }

    fun abrirDialogo(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Desea eliminar")
        builder.setPositiveButton("Aceptar", DialogInterface.OnClickListener{dialog, which ->
            run {
                arreglo.removeAt(idItemSeleccionado)
                println("item ${idItemSeleccionado} eliminado")
            }
        })

        builder.setNegativeButton("Cancelar", null)

        val opciones : Array<String> = resources.getStringArray(R.array.string_array_opciones_dialogo)
        val seleccionPrevia = booleanArrayOf(true, false, false)

        builder.setMultiChoiceItems(opciones, seleccionPrevia, {
            dialog, which, isChecked -> println("Dio clic en el item ${which}")
        })

        val dialogo = builder.create()
        dialogo.show()
    }

}