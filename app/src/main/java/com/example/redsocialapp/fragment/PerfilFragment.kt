package com.example.redsocialapp.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.redsocialapp.ResenasAdapter
import com.example.redsocialapp.databinding.PerfilFragmentBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class PerfilFragment: Fragment(), View.OnClickListener {
    private var _binding: PerfilFragmentBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val db = FirebaseFirestore.getInstance()
    private var rvResenas: RecyclerView? = null
    private var resenaList:MutableList<String> = ArrayList()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = PerfilFragmentBinding.inflate(inflater, container, false)
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
                val app =  it.get("apellido").toString()
                val nombre = it.get("nombre").toString()
               binding.tvEmailPerfil.text = it.get("email").toString()
                binding.tvNombrePerfil.text = nombre + " " + app
                val str: String = it.get("resena").toString()
                str.trim().splitToSequence(',').forEach { element ->
                    resenaList.add(element.replace("[", "").replace("]", ""))
                }
                val adapter = ResenasAdapter(resenaList)
                rvResenas?.adapter = adapter
            }
        return view
    }

    override fun onClick(p0: View?) {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}