import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

abstract class DAO<T>{
    protected val filePath = "datos.txt"
    abstract fun create(entidad: T)
    abstract fun read(id: Int) : T?
    abstract fun update(entidad: T)
    abstract fun delete(id: Int) : Boolean

}