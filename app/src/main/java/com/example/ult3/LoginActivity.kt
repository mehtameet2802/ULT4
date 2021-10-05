package com.example.ult3

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity :AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val login =findViewById<Button>(R.id.btnlogin)
        val email = findViewById<EditText>(R.id.email)
        val username = findViewById<EditText>(R.id.username)
        login.setOnClickListener{
            val email = email.text.toString()
            val username = username.text.toString()
            if (email == "" && username == "")
            { Toast.makeText(this, "Please enter email id and username", Toast.LENGTH_SHORT).show()}
            else if (email == "") { Toast.makeText(this, "Please enter username", Toast.LENGTH_SHORT).show()}
            else if (username=="") {
                Toast.makeText(this, "Please enter username", Toast.LENGTH_SHORT).show()}
            else {
                val intent =Intent(application,MainActivity::class.java)
                startActivity(intent)
                Toast.makeText(this,"Logging in",Toast.LENGTH_SHORT).show()
            }
        }


        val signup = findViewById<TextView>(R.id.sign_up)
        signup.setOnClickListener{
            val intent =Intent(application,activity_sign_up::class.java)
            startActivity(intent)
        }
    }
}