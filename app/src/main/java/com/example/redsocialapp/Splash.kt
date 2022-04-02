package com.example.redsocialapp

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.example.redsocialapp.activity.SelectOptionActivity
import com.google.firebase.FirebaseApp

class Splash: AppCompatActivity() {

    companion object {
        const val TIME_SPLASH_SCREEN = 1200L
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_activity)
        FirebaseApp.initializeApp(this)
        Handler().postDelayed({
            startActivity(Intent(this, SelectOptionActivity::class.java))
            finish()
        }, TIME_SPLASH_SCREEN)
    }
}