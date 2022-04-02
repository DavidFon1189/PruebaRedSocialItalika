package com.example.redsocialapp.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.redsocialapp.R
import com.example.redsocialapp.databinding.SelectOptionActivityBinding

class SelectOptionActivity: AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: SelectOptionActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SelectOptionActivityBinding.inflate(layoutInflater)
        val view = binding.root
        binding.btnSignUpMain.setOnClickListener(this)
        binding.btnUserLoginMain.setOnClickListener(this)
        setContentView(view)
    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.btn_user_login_main -> {
                startActivity(Intent(this, LoginActivity::class.java))
            }
            R.id.btn_sign_up_main -> {
                startActivity(Intent(this, SignupActivity::class.java))
            }
        }
    }
}