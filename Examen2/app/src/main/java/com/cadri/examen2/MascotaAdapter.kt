package com.cadri.examen2

import Mascota
import android.os.Bundle
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.cadri.examen2.data.MascotaDaoMemoria
import com.cadri.examen2.data.PersonaDaoMemoria
import com.cadri.examen2.databinding.MascotaLayoutBinding
import java.text.SimpleDateFormat

class MascotaAdapter(val mascotas: List<Mascota>, val navController: NavController, val idDueno: Int) : RecyclerView.Adapter<MascotaAdapter.MascotaViewHolder>(){
    inner class MascotaViewHolder(val mascotaLayoutBinding: MascotaLayoutBinding): RecyclerView.ViewHolder(mascotaLayoutBinding.root), View.OnCreateContextMenuListener{
        fun bind(mascota: Mascota){
            mascotaLayoutBinding.tvNombre.text = mascota.nombre
            mascotaLayoutBinding.tvFechaNacimiento.text = SimpleDateFormat("dd/MM/yyyy").format(mascota.fechaNacimiento)
            mascotaLayoutBinding.tvPeso.text = mascota.peso.toString()
            mascotaLayoutBinding.tvEsMacho.text = if(mascota.esMacho) "Si" else "No"
        }

        override fun onCreateContextMenu(
            menu: ContextMenu?,
            v: View?,
            menuInfo: ContextMenu.ContextMenuInfo?
        ) {
            menu?.add(adapterPosition, 1, 1, "Editar")
                ?.apply {
                    setOnMenuItemClickListener { menuItem ->
                        val bundle = Bundle()
                        bundle.putInt("id", mascotas[menuItem.groupId].id)
                        bundle.putInt("modo", 2)
                        bundle.putInt("idDueno", idDueno)
                        navController.navigate(R.id.accionGuardarMascota, bundle)
                        true
                    }
                    menu?.add(adapterPosition, 2, 2, "Eliminar")?.apply {
                        setOnMenuItemClickListener { menuItem ->
                            MascotaDaoMemoria(idDueno).delete(mascotas[menuItem.groupId].id).also {
                                notifyItemRemoved(menuItem.groupId)
                            }

                        }
                    }

                }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MascotaViewHolder {
        val mascotaLayoutBinding = MascotaLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val viewHolder = MascotaViewHolder(mascotaLayoutBinding)
        mascotaLayoutBinding.root.setOnCreateContextMenuListener(viewHolder)
        return viewHolder
    }

    override fun onBindViewHolder(holder: MascotaViewHolder, position: Int) {
        holder.bind(mascotas[position])
    }

    override fun getItemCount(): Int {
        return mascotas.size
    }


}