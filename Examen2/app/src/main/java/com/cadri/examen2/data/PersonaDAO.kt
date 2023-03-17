
abstract class PersonaDAO : DAO<Persona>() {
    abstract override fun create(entidad: Persona)
    abstract override fun read(id: Int): Persona?
    abstract override fun delete(id: Int): Boolean
    abstract override fun update(entidad: Persona)
    abstract override fun getCount(): Int
}