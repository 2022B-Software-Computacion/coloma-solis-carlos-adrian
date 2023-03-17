package com.cadri.examen2.data

import Mascota
import MascotaDAO
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class MascotaDaoFirebase(val idDueno: Int){


    val coleccion = FirebaseFirestore.getInstance().collection("personas").document(idDueno.toString()).collection("mascotas")

    private fun docSnapshotToMascota(doc: DocumentSnapshot): Mascota {
        val id = doc.data?.get("id") as Long
        val fechaNacimiento = doc.data?.get("fechaNacimiento") as Timestamp
        val peso = doc.data?.get("peso") as Double
        return Mascota(
            id = id.toInt(),
            nombre = doc.data?.get("nombre") as String,
            fechaNacimiento = fechaNacimiento.toDate(),
            peso = peso.toFloat(),
            esMacho = doc.data?.get("esMacho") as Boolean
        )
    }

    private fun mascotaToMap(mascota: Mascota): Map<String, Any?> {
        return mapOf(
            "id" to mascota.id,
            "nombre" to mascota.nombre,
            "fechaNacimiento" to mascota.fechaNacimiento,
            "peso" to mascota.peso,
            "esMacho" to mascota.esMacho
        )
    }

    fun create(entidad: Mascota) {
        coleccion.document(entidad.id.toString()).set(mascotaToMap(entidad))
    }

    fun read(id: Int): Flow<Mascota> {
        val callbackFlow = callbackFlow<Mascota> {
            val listener = coleccion.document(id.toString()).addSnapshotListener { value, error ->
                if (error != null) {
                    println("Error al leer de la base de datos: $error")
                    return@addSnapshotListener
                }
                val mascota = docSnapshotToMascota(value!!)
                trySend(mascota)
            }
            awaitClose { listener.remove() }
        }

        return callbackFlow
    }

    fun delete(id: Int): Boolean {
        coleccion.document(id.toString()).delete()
        return true
    }

    fun update(entidad: Mascota) {
        coleccion.document(entidad.id.toString()).set(mascotaToMap(entidad))
    }

    fun getAll(): Flow<List<Mascota>> {
        val callflow = callbackFlow {
            val listener = coleccion.addSnapshotListener { value, error ->
                if (error != null) {
                    println("Error al leer de la base de datos: $error")
                    return@addSnapshotListener
                }
                val mascotas = value!!.map { docSnapshotToMascota(it) }
                trySend(mascotas)
            }
            awaitClose { listener.remove() }
        }

        return callflow
    }

}