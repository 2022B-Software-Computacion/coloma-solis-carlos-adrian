abstract class MascotaDAO(var idDueno: Int) : DAO<Mascota>() {

    abstract override fun create(entidad: Mascota)
    abstract override fun read(id: Int): Mascota?
    abstract override fun delete(id: Int): Boolean
    abstract override fun update(entidad: Mascota)


}