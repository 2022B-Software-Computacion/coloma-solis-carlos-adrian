package com.cadri.examen2

import Mascota
import MascotaDAO
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.cadri.examen2.data.MascotaDaoMemoria
import com.cadri.examen2.databinding.FragmentEditarMascotaBinding
import java.text.SimpleDateFormat
import java.util.*

class EditarMascotaFragment : Fragment(){

    private var _binding : FragmentEditarMascotaBinding? = null
    private val binding get() = _binding!!

    private val modo by lazy {
        arguments?.getInt("modo") ?: MODO_AGREGAR
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?) : View?{
        _binding = FragmentEditarMascotaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val idDueno = requireArguments().getInt("idDueno")
        println("idDueno: $idDueno")
        val dao = MascotaDaoMemoria(idDueno)
        val actividad = activity as AppCompatActivity
        val actionBar = actividad.supportActionBar
        if(modo == MODO_EDITAR){
            actionBar?.title = "Editar Mascota"
            val id = arguments?.getInt("id") ?: 0
            val persona = dao.read(id)
            if(persona != null){
                binding.editTextTextPersonName.setText(persona.nombre)
                binding.editTextDate.setText(SimpleDateFormat("dd/MM/yyyy").format(persona.fechaNacimiento))
                binding.checkBox.isChecked = persona.esMacho
                binding.editTextNumberDecimal.setText(persona.peso.toString())
            }
        }else
            actionBar?.title = "Agregar Mascota"

        binding.button.setOnClickListener {
            val nombre: String = binding.editTextTextPersonName.text.toString()
            val fechaNacimiento: Date = SimpleDateFormat("dd/MM/yyyy").parse(binding.editTextDate.text.toString())
            val esMacho: Boolean = binding.checkBox.isChecked
            val peso: Float = binding.editTextNumberDecimal.text.toString().toFloat()

            if(modo == MODO_AGREGAR){
                dao.create(Mascota(nombre= nombre, fechaNacimiento = fechaNacimiento, esMacho = esMacho, peso = peso))
            }else{
                val id = arguments?.getInt("id") ?: 0
                dao.update(Mascota(id, nombre, fechaNacimiento, esMacho, peso))
            }
            findNavController().popBackStack()
        }
    }


}