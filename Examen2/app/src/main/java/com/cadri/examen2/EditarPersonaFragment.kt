package com.cadri.examen2

import Persona
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.cadri.examen2.data.PersonaDaoFirebase
import com.cadri.examen2.data.PersonaDaoMemoria
import com.cadri.examen2.databinding.FragmentEditarPersonaBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import java.text.SimpleDateFormat
import java.util.Date


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */

const val MODO_AGREGAR = 1
const val MODO_EDITAR = 2
class EditarPersonaFragment : Fragment() {
    private val dao = PersonaDaoFirebase

    private var _binding: FragmentEditarPersonaBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val modo by lazy {
        arguments?.getInt("modo") ?: MODO_AGREGAR
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentEditarPersonaBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val actividad = activity as AppCompatActivity
        val actionBar = actividad.supportActionBar

        lifecycleScope.launchWhenStarted {
            if(modo == MODO_EDITAR){
                actionBar?.title = "Editar Persona"
                val id = arguments?.getInt("id") ?: 0
                dao.read(id).collect{
                    val persona = it
                    binding.editTextTextPersonName.setText(persona.nombre)
                    binding.editTextDate.setText(SimpleDateFormat("dd/MM/yyyy").format(persona.fechaNacimiento))
                    binding.checkBox.isChecked = persona.estaCasado
                    binding.editTextNumberDecimal.setText(persona.peso.toString())
                }
            }else
                actionBar?.title = "Agregar Persona"
        }


        binding.button.setOnClickListener {
            val nombre: String = binding.editTextTextPersonName.text.toString()
            val fechaNacimiento: Date = SimpleDateFormat("dd/MM/yyyy").parse(binding.editTextDate.text.toString())
            val estaCasado: Boolean = binding.checkBox.isChecked
            val peso: Float = binding.editTextNumberDecimal.text.toString().toFloat()

            if(modo == MODO_AGREGAR){
                dao.create(Persona(nombre= nombre, fechaNacimiento = fechaNacimiento, estaCasado = estaCasado, peso = peso))
            }else{
                val id = arguments?.getInt("id") ?: 0
                dao.update(Persona(id, nombre, fechaNacimiento, estaCasado, peso))
            }
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}