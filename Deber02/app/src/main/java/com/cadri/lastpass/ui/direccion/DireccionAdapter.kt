package com.cadri.lastpass.ui.direccion

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.cadri.lastpass.R
import com.cadri.lastpass.data.Direccion
import com.cadri.lastpass.data.DireccionDAO
import io.getstream.avatarview.AvatarView

class DireccionAdapter : RecyclerView.Adapter<DireccionAdapter.DireccionViewHolder>() {
    private val direcciones = DireccionDAO.getDirecciones()

    inner class DireccionViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val view = itemView

        val tvNombre = view.findViewById<TextView>(R.id.tv_nombre)
        val tvUsuario = view.findViewById<TextView>(R.id.tv_usuario)
        val avatar = view.findViewById<AvatarView>(R.id.avatar)

        fun bind(direccion: Direccion) {
            tvNombre.text = direccion.nombre
            tvUsuario.text = "Direccion"
            avatar.load(R.drawable.ic_baseline_book_24)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DireccionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_contrasena, parent, false)
        println("onCreateViewHolder")
        return DireccionViewHolder(view)
    }

    override fun onBindViewHolder(holder: DireccionViewHolder, position: Int) {
        val direccion = direcciones[position]
        holder.bind(direccion)
    }

    override fun getItemCount(): Int {
        return direcciones.size
    }
}