import kotlinx.serialization.SerializationException
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File

object PersonaDAO : DAO<Persona>() {
    val personas : ArrayList<Persona>
    init{
        personas =
            try {
                Json.decodeFromString<ArrayList<Persona>>(File(filePath).readText()).also { println("se leyo archivo") }

            } catch (e: SerializationException) {
                arrayListOf()
            }

    }
    override fun create(entidad: Persona) {
        personas.add(entidad)
        save()
    }

    override fun read(id: Int) : Persona?{
        return personas.find { persona -> persona.id == id }
    }

    override fun delete(id: Int) : Boolean {
        val seRemovio = personas.removeIf{persona -> persona.id == id}
        save()
        return seRemovio
    }

    override fun update(entidad: Persona) {
        val entidadUpdate = PersonaDAO.read(entidad.id)
        if(entidadUpdate != null){
            entidadUpdate.nombre = entidad.nombre
            entidadUpdate.fechaNacimiento = entidad.fechaNacimiento
            entidadUpdate.estaCasado = entidad.estaCasado
            entidadUpdate.peso = entidad.peso
        }


        save()
    }

    fun save(){
        File(filePath).writeText(Json.encodeToString(personas))

    }


}