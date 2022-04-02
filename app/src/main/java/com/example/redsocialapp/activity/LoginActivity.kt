package com.example.redsocialapp.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.redsocialapp.R
import com.example.redsocialapp.databinding.LoginActivityBinding
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity: AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: LoginActivityBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LoginActivityBinding.inflate(layoutInflater)
        val view = binding.root
        binding.btnUserLoginMain.setOnClickListener(this)
        auth = Firebase.auth
        setContentView(view)
    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.btn_user_login_main -> {
                val user = binding.edtUserLogin.text.toString().trim()
                val pass = binding.edtPass.text.toString().trim()
                auth.signInWithEmailAndPassword(user, pass).
                addOnCompleteListener{
                    if (it.isSuccessful){
                        val intent = Intent(this, MainActivity::class.java)
                        intent.putExtra("email", user)
                        startActivity(intent)
                    } else {
                        val buildDialog = AlertDialog.Builder(this)
                        buildDialog.setTitle("ERROR")
                        buildDialog.setMessage("Error de autenticacion")
                        buildDialog.setPositiveButton("Aceptar", null)
                        val dialog: AlertDialog = buildDialog.create()
                        dialog.show()
                    }
                }
            }
        }
    }
}