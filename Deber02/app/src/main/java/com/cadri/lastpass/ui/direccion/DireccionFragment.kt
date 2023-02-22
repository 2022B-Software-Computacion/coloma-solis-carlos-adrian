package com.cadri.lastpass.ui.direccion

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.cadri.lastpass.databinding.FragmentGalleryBinding

class DireccionFragment : Fragment() {

    private var _binding: FragmentGalleryBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvDirecciones.layoutManager = LinearLayoutManager(context)
        binding.rvDirecciones.adapter = DireccionAdapter()
        binding.rvDirecciones.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}