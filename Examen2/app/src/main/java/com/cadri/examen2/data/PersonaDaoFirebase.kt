package com.cadri.examen2.data

import Persona
import PersonaDAO
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.runBlocking
import java.util.*


object PersonaDaoFirebase{
    val coleccion = FirebaseFirestore.getInstance().collection("personas")
    private fun personaToMap(persona: Persona): Map<String, Any?> {
        return mapOf(
            "id" to persona.id,
            "nombre" to persona.nombre,
            "fechaNacimiento" to persona.fechaNacimiento,
            "peso" to persona.peso,
            "estaCasado" to persona.estaCasado
        )
    }

    private fun docSnapshotToPersona(doc: DocumentSnapshot): Persona {
        val id = doc.data?.get("id") as Long
        val fechaNacimiento = doc.data?.get("fechaNacimiento") as Timestamp
        val peso = doc.data?.get("peso") as Double
        return Persona(
            id = id.toInt(),
            nombre = doc.data?.get("nombre") as String,
            fechaNacimiento = fechaNacimiento.toDate(),
            peso = peso.toFloat(),
            estaCasado = doc.data?.get("estaCasado") as Boolean
        )
    }

    fun create(entidad: Persona) {
        coleccion.document(entidad.id.toString()).set(personaToMap(entidad))
    }

    fun read(id: Int): Flow<Persona> {
        val callbackFlow = callbackFlow<Persona> {
            val listener = coleccion.document(id.toString()).addSnapshotListener { value, error ->
                if (error != null) {
                    println("Error al leer de la base de datos: $error")
                    return@addSnapshotListener
                }
                val persona = docSnapshotToPersona(value!!)
                trySend(persona)
            }
            awaitClose { listener.remove() }
        }

        return callbackFlow
    }

    fun delete(id: Int): Boolean {
        coleccion.document(id.toString()).delete()
        return true
    }

    fun update(entidad: Persona) {
        coleccion.document(entidad.id.toString()).set(personaToMap(entidad))
    }

    fun getAll(): Flow<List<Persona>> {
        val callbackFlow = callbackFlow{
            val listener = coleccion.addSnapshotListener { value, error ->
                if (error != null) {
                    println("Error al leer de la base de datos: $error")
                    return@addSnapshotListener
                }
                val personas = value?.documents?.map { doc -> docSnapshotToPersona(doc as QueryDocumentSnapshot) } ?: listOf()
                trySend(personas)
            }
            awaitClose { listener.remove() }
        }

        return callbackFlow
    }
}