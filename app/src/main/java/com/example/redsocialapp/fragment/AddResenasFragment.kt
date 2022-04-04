package com.example.redsocialapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.redsocialapp.R
import com.example.redsocialapp.databinding.AddResenaFragmentBinding
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

class AddResenasFragment : Fragment(), View.OnClickListener {

    private var _binding: AddResenaFragmentBinding? = null
    private val binding get() = _binding!!
    private val db = FirebaseFirestore.getInstance()
    private var email: String = ""
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = AddResenaFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.btnAddResena.setOnClickListener(this)
        val data = arguments
        val getEmail = data?.get("email")
        email = getEmail.toString()
        return view
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btn_add_resena -> {
                if (!binding.edtAddResena.text.isEmpty()) {
                    val resena = binding.edtAddResena.text.toString()
                    db.collection("users").document(email)
                        .update("resena", FieldValue.arrayUnion(resena)).addOnCompleteListener() {
                            if (it.isSuccessful) {
                                Toast.makeText(
                                    activity,
                                    "Resña agregada exitosamente",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                                binding.edtAddResena.setText("")
                            } else {
                                Toast.makeText(
                                    activity,
                                    "Fallo al ingresar la reseña",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            }
                        }
                } else{
                    Toast.makeText(activity, "Agregar reseña", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}