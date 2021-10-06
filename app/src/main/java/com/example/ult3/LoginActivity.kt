package com.example.ult3

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.ult3.fragment.Details1
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity :AppCompatActivity(){


    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mAuth = Firebase.auth

        val login =findViewById<Button>(R.id.btnlogin)
        var email = findViewById<EditText>(R.id.email)
        var password = findViewById<EditText>(R.id.password1)
        login.setOnClickListener{
            val email1 = email.text.trim().toString()
            val password1 = password.text.trim().toString()
            if (email1 == "" &&  password1== "")
            { Toast.makeText(this, "Please enter email id and username", Toast.LENGTH_SHORT).show()}
            else if (email1 == "") { Toast.makeText(this, "Please enter username", Toast.LENGTH_SHORT).show()}
            else if (password1 =="") {
                Toast.makeText(this, "Please enter username", Toast.LENGTH_SHORT).show()}
            else {emailSignIn(email1,password1)
                email.setText("")
                password.setText("")
            }
        }

        val signup = findViewById<TextView>(R.id.sign_up)
        signup.setOnClickListener{
            val intent =Intent(application,activity_sign_up::class.java)
            startActivity(intent)
            email.setText("")
            password.setText("")
        }
    }

    private fun emailSignIn(email:String, password:String) {
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val intent = Intent(application, MainActivity::class.java)
                    intent.putExtra("email", email)
                    startActivity(intent)
                    Toast.makeText(this, "Logging you in", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Wrong email id or password", Toast.LENGTH_SHORT).show()
                }
            }
    }

}