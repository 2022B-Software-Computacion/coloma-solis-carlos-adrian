import kotlinx.serialization.Serializable
import java.text.SimpleDateFormat
import java.util.Date

@Serializable()
data class Persona(val id: Int, val nombre: String, @Serializable(with = DateSerializer::class) val fechaNacimiento: Date, val estaCasado: Boolean, val peso: Float){
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