package com.cadri.lastpass

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cadri.lastpass.data.Contrasena
import com.cadri.lastpass.data.ContrasenaDAO

import io.getstream.avatarview.AvatarView

class ContrasenaAdapter : RecyclerView.Adapter<ContrasenaAdapter.ContrasenaViewHolder>() {
    private val contrasenas = ContrasenaDAO.getContrasenas()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContrasenaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_contrasena, parent, false)
        println("onCreateViewHolder")
        return ContrasenaViewHolder(view)
    }

    override fun onBindViewHolder(holder: ContrasenaViewHolder, position: Int) {
        val contrasena = contrasenas[position]
        holder.bind(contrasena)
    }

    override fun getItemCount(): Int {
        return contrasenas.size
    }

    inner class ContrasenaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val view = itemView

        val tvNombre = view.findViewById<TextView>(R.id.tv_nombre)
        val tvUsuario = view.findViewById<TextView>(R.id.tv_usuario)
        val avatar = view.findViewById<AvatarView>(R.id.avatar)

        fun bind(contrasena: Contrasena) {
            tvNombre.text = contrasena.nombre
            tvUsuario.text = contrasena.usuario
            avatar.avatarInitials = contrasena.nombre[0].toString()
        }
    }
}