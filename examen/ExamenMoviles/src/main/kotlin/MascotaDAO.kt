class MascotaDAO(val mascotas: ArrayList<Mascota>) : DAO<Mascota>() {
    override fun create(entidad: Mascota) {
        mascotas.add(entidad)
        save()
    }
    override fun read(id: Int): Mascota? {
        return mascotas.find{it.id == id}
    }

    override fun delete(id: Int): Boolean {
        val seRemovio = mascotas.removeIf { it.id == id }
        if(seRemovio)
            save()
        return seRemovio
    }

    override fun update(entidad: Mascota) {
        delete(entidad.id)
        create(entidad)
    }

    fun save(){
        PersonaDAO.save()
    }

}