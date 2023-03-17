package com.cadri.examen2.data

import Mascota
import Persona
import PersonaDAO
import java.text.SimpleDateFormat

object PersonaDaoMemoria : PersonaDAO() {
    private val personas : MutableList<Persona>
    val dateFormat = SimpleDateFormat("dd/MM/yyyy")
    init{
        personas = mutableListOf(
            Persona(1, "Carlos", dateFormat.parse("01/01/1990"), true, 80.0f),
            Persona(2, "Juan", dateFormat.parse("01/01/1990"), true, 80.0f),
            Persona(3, "Pedro", dateFormat.parse("01/01/1990"), true, 80.0f),
        )

        personas[0].mascotas.add(Mascota(1, "Firulais", dateFormat.parse("01/01/1990"), false, 10.1f))
        personas[1].mascotas.add(Mascota(2, "Garfield", dateFormat.parse("01/01/1990"), true, 10.2f))
        personas[2].mascotas.add(Mascota(3, "Piolin", dateFormat.parse("01/01/1990"), false, 10.3f))

    }

    override fun create(entidad: Persona) {
        personas.add(entidad)

    }

    override fun read(id: Int) : Persona?{
        return personas.find { persona -> persona.id == id }
    }

    override fun delete(id: Int) : Boolean {
        val seRemovio = personas.removeIf{persona -> persona.id == id}
        return seRemovio
    }

    override fun update(entidad: Persona) {
        val persona = personas.find { persona -> persona.id == entidad.id }
        persona?.nombre = entidad.nombre
        persona?.fechaNacimiento = entidad.fechaNacimiento
        persona?.peso = entidad.peso
        persona?.estaCasado = entidad.estaCasado
    }

    override fun getCount(): Int {
        return personas.size
    }

    override fun getAll(): List<Persona> {
        return personas
    }
}