package com.cadri.examen2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.cadri.examen2.data.MascotaDaoFirebase
import com.cadri.examen2.data.MascotaDaoMemoria
import com.cadri.examen2.data.PersonaDaoMemoria.getAll
import com.cadri.examen2.databinding.FragmentMascotasBinding

class MascotasFragment : Fragment() {
    private var _binding: FragmentMascotasBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMascotasBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val idDueno: Int = requireArguments().getInt("id")
        println("idDueno: $idDueno")
        val dao = MascotaDaoFirebase(idDueno)
        val navController = findNavController()
        binding.rvMascotas.layoutManager = LinearLayoutManager(context)
        lifecycleScope.launchWhenStarted {
            dao.getAll().collect { mascotas ->
                binding.rvMascotas.adapter = MascotaAdapter(mascotas, navController, idDueno)
            }
        }

        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.accionGuardarMascota, Bundle().apply {
                putInt("modo", MODO_AGREGAR)
                putInt("idDueno", idDueno)
            })
        }

    }
}