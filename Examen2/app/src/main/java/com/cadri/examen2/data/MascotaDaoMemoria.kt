package com.cadri.examen2.data

import Mascota
import MascotaDAO

class MascotaDaoMemoria(idDueno: Int) : MascotaDAO(idDueno){
    override fun create(entidad: Mascota) {
        val dueno = PersonaDaoMemoria.read(idDueno)
        if(dueno != null){
            dueno.mascotas.add(entidad)
            println("Mascota creada: $entidad")
        }
        else{
            println("No se encontro el dueno con id $idDueno")
        }

    }

    override fun read(id: Int): Mascota? {
        return PersonaDaoMemoria.read(idDueno)?.mascotas?.find { mascota -> mascota.id == id }
    }

    override fun delete(id: Int): Boolean {
        val seRemovio = PersonaDaoMemoria.read(idDueno)?.mascotas?.removeIf{mascota -> mascota.id == id} ?: false
        return seRemovio
    }

    override fun update(entidad: Mascota) {
        val mascota = PersonaDaoMemoria.read(idDueno)?.mascotas?.find { mascota -> mascota.id == entidad.id }
        mascota?.nombre = entidad.nombre
        mascota?.fechaNacimiento = entidad.fechaNacimiento
        mascota?.peso = entidad.peso
        mascota?.esMacho = entidad.esMacho
    }

    override fun getCount(): Int {
        return PersonaDaoMemoria.read(idDueno)?.mascotas?.size ?: 0
    }

    override fun getAll(): List<Mascota> {
        return PersonaDaoMemoria.read(idDueno)?.mascotas ?: listOf()
    }

}