package com.cadri.lastpass.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cadri.lastpass.ContrasenaAdapter
import com.cadri.lastpass.R


class ContrasenasFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contrasenas, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val rvContrasenas = view.findViewById<RecyclerView>(R.id.rv_contrasenas)
        rvContrasenas.adapter = ContrasenaAdapter()
        rvContrasenas.layoutManager = LinearLayoutManager(context)
        rvContrasenas.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
    }


}