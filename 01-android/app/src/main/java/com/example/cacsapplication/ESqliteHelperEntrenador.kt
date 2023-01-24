package com.example.cacsapplication

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class ESqliteHelperEntrenador(contexto: Context?): SQLiteOpenHelper(contexto,
    "moviles",
    null,
    1){
    override fun onCreate(db: SQLiteDatabase?) {
        val scriptSQLCrearTablaEntrenador =
            """CREATE TABLE ENTRENADOR(
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre VARCHAR(50),
                descripcion VARCHAR(50))
            """.trimIndent()
        db?.execSQL(scriptSQLCrearTablaEntrenador)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    fun crearEntrenador(nombre: String, descripcion: String): Boolean{
        val basedatosEscritura = writableDatabase
        val valoresAGuardar = ContentValues()
        valoresAGuardar.put("nombre", nombre)
        valoresAGuardar.put("descripcion", descripcion)

        val resultadoGuardar = basedatosEscritura.insert(
            "ENTRENADOR",null, valoresAGuardar
        )

        basedatosEscritura.close()
        return resultadoGuardar.toInt() != -1
    }

    fun eliminarEntrenadorFormulario(id: Int): Boolean{
        val conexionEscritura = writableDatabase

        val resultadoEliminacion = conexionEscritura.delete("ENTRENADOR", "id=?", arrayOf(id.toString()))
        conexionEscritura.close()

        return resultadoEliminacion != -1
    }

    fun actualizarEntrenadorFormulario(nombre:String, descripcion: String, idActualizar: Int): Boolean{
        val conexionEscritura = writableDatabase
        val valoresAActualizar = ContentValues()
        valoresAActualizar.put("nombre", nombre)
        valoresAActualizar.put("descripcion", descripcion)
        val resultadoActualizacion = conexionEscritura.update("ENTRENADOR", valoresAActualizar, "id=?", arrayOf(idActualizar.toString()))
        conexionEscritura.close()
        return resultadoActualizacion != -1
    }

    fun consultarEntrenadorPorId(id: Int): BEntrenador{
        val baseDatosLectura = readableDatabase
        val scriptConsultarUsuario = "SELECT * FROM ENTRENADOR WHERE ID = ?"
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(scriptConsultarUsuario, arrayOf(id.toString()))

        val existeUsuario = resultadoConsultaLectura.moveToFirst()
        var usuarioEncontrado = BEntrenador(0, "", "")
        val arreglo = arrayListOf<BEntrenador>()
        if(existeUsuario){
            do{
                val id = resultadoConsultaLectura.getInt(0)
                val nombre = resultadoConsultaLectura.getString(1)
                val descripcion = resultadoConsultaLectura.getString(2)
                if(id != null){
                    usuarioEncontrado = BEntrenador(0, "", "")
                    usuarioEncontrado.id = id
                    usuarioEncontrado.nombre = nombre
                    usuarioEncontrado.descripcion = descripcion
                    arreglo.add(usuarioEncontrado)
                }
            } while(resultadoConsultaLectura.moveToNext())
        }

        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        return usuarioEncontrado
    }
}