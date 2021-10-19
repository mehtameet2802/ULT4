package com.example.ult3

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SplashScreen: AppCompatActivity() {
    val mAuth = Firebase.auth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        supportActionBar?.hide()

        Handler(Looper.getMainLooper()).postDelayed({
            if(mAuth.currentUser == null)
            {
                val intent = Intent(this,LoginActivity::class.java)
                startActivity(intent)
            }
            else{
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)}
            finish()
        },2000)
    }
}