import kotlinx.serialization.Serializable
import java.util.*

@Serializable()
data class Mascota(val id: Int, val nombre: String, @Serializable(with=DateSerializer::class)val fechaNacimiento: Date, val esMacho: Boolean, val peso: Float) {
    val dueno: Persona?
        get() {
            return PersonaDAO.personas.find{it.mascotas.contains(this)}
        }

    override fun toString(): String {
        return "Id = $id Nombre = $nombre"
    }
}