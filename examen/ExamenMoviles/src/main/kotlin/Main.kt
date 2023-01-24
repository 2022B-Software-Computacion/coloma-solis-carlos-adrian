import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializer
import kotlinx.serialization.UseSerializers
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encodeToString
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.Json
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.*

@Serializer(forClass = Date::class)
object DateSerializer: KSerializer<Date>{
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("Date", PrimitiveKind.STRING)
    private val df: DateFormat = SimpleDateFormat("dd/MM/yyyy")

    override fun serialize(encoder: Encoder, value: Date) {
        encoder.encodeString(df.format(value))
    }

    override fun deserialize(decoder: Decoder): Date {
        return df.parse(decoder.decodeString())
    }

}


fun main(args: Array<String>) {
    iniciarGUI()
    val persona = Persona(4, "Adrian",  "29/04/2001", false, 50.1f)

    val mascota = Mascota(5, "Max",  Date.from(Instant.now()), true, 3.1f)
    persona.mascotas.add(mascota)
    //PersonaDAO.create(persona)

    println(PersonaDAO.read(4))
    //PersonaDAO.delete(4)

}