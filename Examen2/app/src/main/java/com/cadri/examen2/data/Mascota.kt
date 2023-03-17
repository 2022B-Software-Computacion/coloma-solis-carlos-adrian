import java.util.*
import kotlin.random.Random

data class Mascota(var id: Int = Random.nextInt(), var nombre: String, var fechaNacimiento: Date, var esMacho: Boolean, var peso: Float) {

    override fun toString(): String {
        return "Id = $id Nombre = $nombre"
    }
}