
import java.text.SimpleDateFormat
import java.util.Date
import kotlin.random.Random


data class Persona(val id: Int = Random.nextInt(), var nombre: String, var fechaNacimiento: Date, var estaCasado: Boolean, var peso: Float){
    val mascotas : ArrayList<Mascota> = ArrayList()
    constructor(id: Int, nombre: String, fechaNacimiento: String, estaCasado: Boolean, peso: Float ) : this(
        id, nombre,
        SimpleDateFormat("dd/MM/yyyy").parse(fechaNacimiento),
        estaCasado, peso
    ) {

    }

    override fun toString(): String {
        return "Id = $id Nombre = $nombre"
    }
}