package com.cadri.examen2

import Persona
import PersonaDAO
import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.cadri.examen2.data.PersonaDaoFirebase
import com.cadri.examen2.data.PersonaDaoMemoria
import com.cadri.examen2.databinding.PersonaLayoutBinding
import java.text.SimpleDateFormat


class PersonaAdapter(val personas: List<Persona>, val navController: NavController) :
    RecyclerView.Adapter<PersonaAdapter.PersonaViewHolder>() {

    inner class PersonaViewHolder(val personaLayoutBinding: PersonaLayoutBinding) :
        RecyclerView.ViewHolder(personaLayoutBinding.root), View.OnCreateContextMenuListener {
        val tvNombre: TextView = personaLayoutBinding.tvNombre

        fun bind(persona: Persona) {
            tvNombre.text = persona.nombre
            personaLayoutBinding.root.setOnLongClickListener { view ->
                view.showContextMenu()
            }

            personaLayoutBinding.tvFechaNacimiento.text =
                SimpleDateFormat("dd/MM/yyyy").format(persona.fechaNacimiento)
            personaLayoutBinding.tvPeso.text = persona.peso.toString()
            personaLayoutBinding.tvEstaCasado.text = if (persona.estaCasado) "Si" else "No"
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
                        bundle.putInt("id", personas[menuItem.groupId].id)
                        bundle.putInt("modo", 2)
                        navController.navigate(R.id.accionGuardar, bundle)
                        true
                    }
                    menu?.add(adapterPosition, 2, 2, "Eliminar")?.apply {
                        setOnMenuItemClickListener { menuItem ->
                            PersonaDaoFirebase.delete(personas[menuItem.groupId].id).also {
                                notifyItemRemoved(menuItem.groupId)
                            }

                        }
                    }

                    menu?.add(adapterPosition, 3, 3, "Mascotas")?.apply {
                        setOnMenuItemClickListener { menuItem ->
                            navController.navigate(R.id.accion_ver_mascotas, Bundle().apply {
                                putInt("id", personas[menuItem.groupId].id)
                            })
                            true
                        }

                    }
                }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonaViewHolder {
        val personaLayoutBinding =
            PersonaLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val viewHolder = PersonaViewHolder(personaLayoutBinding)
        personaLayoutBinding.root.setOnCreateContextMenuListener(viewHolder)
        return viewHolder
    }

    override fun onBindViewHolder(holder: PersonaViewHolder, position: Int) {
        val persona = personas[position]
        if (persona == null) {
            println("No hay persona en la posicion ${position}")
        } else {
            holder.bind(persona)
            println("Persona bindeada $persona")
        }

    }

    override fun getItemCount(): Int {
        return personas.size
    }

}
