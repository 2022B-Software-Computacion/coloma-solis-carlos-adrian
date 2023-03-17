package com.cadri.examen2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.cadri.examen2.data.PersonaDaoFirebase
import com.cadri.examen2.data.PersonaDaoMemoria
import com.cadri.examen2.databinding.FragmentFirstBinding
import kotlinx.coroutines.flow.collect

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class PersonasFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvPersonas.layoutManager = LinearLayoutManager(context)
        val navController = findNavController()

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            PersonaDaoFirebase.getAll().collect { personas ->
                binding.rvPersonas.adapter = PersonaAdapter(personas, navController)
            }
        }

        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.accionGuardar, Bundle().apply {
                putInt("modo", MODO_AGREGAR)
            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}