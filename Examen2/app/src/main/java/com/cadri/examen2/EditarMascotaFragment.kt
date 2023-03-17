package com.cadri.examen2

import Mascota
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.cadri.examen2.data.MascotaDaoFirebase
import com.cadri.examen2.databinding.FragmentEditarMascotaBinding
import kotlinx.coroutines.flow.collect
import java.text.SimpleDateFormat
import java.util.*

class EditarMascotaFragment : Fragment() {

    private var _binding: FragmentEditarMascotaBinding? = null
    private val binding get() = _binding!!

    private val modo by lazy {
        arguments?.getInt("modo") ?: MODO_AGREGAR
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditarMascotaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val idDueno = requireArguments().getInt("idDueno")
        println("idDueno: $idDueno")
        val dao = MascotaDaoFirebase(idDueno)
        val actividad = activity as AppCompatActivity
        val actionBar = actividad.supportActionBar

        lifecycleScope.launchWhenCreated {

            if (modo == MODO_EDITAR) {
                actionBar?.title = "Editar Mascota"
                val id = arguments?.getInt("id") ?: 0
                dao.read(id).collect {
                    val mascota = it
                    binding.editTextTextPersonName.setText(mascota.nombre)
                    binding.editTextDate.setText(SimpleDateFormat("dd/MM/yyyy").format(mascota.fechaNacimiento))
                    binding.checkBox.isChecked = mascota.esMacho
                    binding.editTextNumberDecimal.setText(mascota.peso.toString())
                }
            } else
                actionBar?.title = "Agregar Mascota"

        }


        binding.button.setOnClickListener {
            val nombre: String = binding.editTextTextPersonName.text.toString()
            val fechaNacimiento: Date =
                SimpleDateFormat("dd/MM/yyyy").parse(binding.editTextDate.text.toString())
            val esMacho: Boolean = binding.checkBox.isChecked
            val peso: Float = binding.editTextNumberDecimal.text.toString().toFloat()

            if (modo == MODO_AGREGAR) {
                dao.create(
                    Mascota(
                        nombre = nombre,
                        fechaNacimiento = fechaNacimiento,
                        esMacho = esMacho,
                        peso = peso
                    )
                )
            } else {
                val id = arguments?.getInt("id") ?: 0
                dao.update(Mascota(id, nombre, fechaNacimiento, esMacho, peso))
            }
            findNavController().popBackStack()
        }
    }


}