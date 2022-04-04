package com.example.redsocialapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.redsocialapp.adapter.ResenasAdapter
import com.example.redsocialapp.databinding.ResenasComunidadBinding
import com.google.firebase.firestore.FirebaseFirestore

class ResenasCommunity : Fragment() {
    private var _binding: ResenasComunidadBinding? = null
    private val binding get() = _binding!!
    private val db = FirebaseFirestore.getInstance()
    private var rvResenas: RecyclerView? = null
    private var resenaList: MutableList<String> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ResenasComunidadBinding.inflate(inflater, container, false)
        val view = binding.root
        rvResenas = binding.rvResena.recyclerView
        rvResenas?.setHasFixedSize(true)
        val layoutManager: RecyclerView.LayoutManager =
            LinearLayoutManager(activity)
        rvResenas?.layoutManager = layoutManager
        rvResenas?.itemAnimator = DefaultItemAnimator()
        val data = arguments
        val getEmail = data?.get("email")
        val email: String = getEmail.toString()
        db.collection("users").document(email).get()
            .addOnSuccessListener {
                val str: String = it.get("resena").toString()
                str.trim().splitToSequence(',').forEach { element ->
                    resenaList.add(element.replace("[", "").replace("]", ""))
                }
                val adapter = ResenasAdapter(resenaList)
                rvResenas?.adapter = adapter
            }
        return view
    }
}