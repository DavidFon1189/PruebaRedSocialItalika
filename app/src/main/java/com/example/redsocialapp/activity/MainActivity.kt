package com.example.redsocialapp.activity

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.redsocialapp.R
import com.example.redsocialapp.databinding.MainActivityBinding
import com.example.redsocialapp.fragment.AddResenasFragment
import com.example.redsocialapp.fragment.PerfilFragment
import com.example.redsocialapp.fragment.QuestionsFragment
import com.example.redsocialapp.fragment.ResenasCommunity
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: MainActivityBinding
    private var email: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        email = intent.getStringExtra("email").toString()
        val fragment = PerfilFragment()
        val bundle = Bundle()
        bundle.putString("email", email)
        fragment.arguments = bundle
        replaceFragment(fragment)
        binding.bottonNavigation.setOnNavigationItemSelectedListener(
            mOnNavigationItemSelectedListener
        )
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.content_main_frameLayout, fragment)
            .commit()
    }

    private val mOnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_add -> {
                    val fragment = AddResenasFragment()
                    val bundle = Bundle()
                    bundle.putString("email", email)
                    fragment.arguments = bundle
                    replaceFragment(fragment)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.nav_perfil -> {
                    val fragment = PerfilFragment()
                    val bundle = Bundle()
                    bundle.putString("email", email)
                    fragment.arguments = bundle
                    replaceFragment(fragment)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.nav_resenas -> {
                    val fragment = ResenasCommunity()
                    val bundle = Bundle()
                    bundle.putString("email", email)
                    fragment.arguments = bundle
                    replaceFragment(fragment)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.nav_graphic -> {
                    replaceFragment(QuestionsFragment())
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }

    override fun onBackPressed() {
        val buildDialog = AlertDialog.Builder(this)
        buildDialog.setTitle("Advertencia")
        buildDialog.setMessage("Desea cerrar la sesión?")
        buildDialog.setPositiveButton("Aceptar", DialogInterface.OnClickListener { dialog, id ->
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        })
        buildDialog.setNegativeButton("Cancelar", null)
        val dialog: AlertDialog = buildDialog.create()
        dialog.show()
    }
}