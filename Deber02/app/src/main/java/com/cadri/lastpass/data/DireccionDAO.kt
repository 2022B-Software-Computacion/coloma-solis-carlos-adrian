package com.cadri.lastpass.data

object DireccionDAO {
    private val direcciones = mutableListOf<Direccion>()

    init{
        direcciones.apply {
            add(Direccion("Casa", "Calle 1", "Calle 2"))
            add(Direccion("Trabajo", "Calle 1", "Calle 2"))
            add(Direccion("Escuela", "Calle 1", "Calle 2"))
        }
    }
    fun getDirecciones() : List<Direccion>{
        return direcciones
    }
}