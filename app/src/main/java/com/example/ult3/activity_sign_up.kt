package com.example.ult3

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class activity_sign_up:AppCompatActivity() {

    val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        val Email = findViewById<EditText>(R.id.email2)
        val Username = findViewById<EditText>(R.id.username2)
        val Birthdate = findViewById<EditText>(R.id.birthdate)
        val sign_up = findViewById<Button>(R.id.btn_sign_up)
        sign_up.setOnClickListener{
            val intent = Intent(application,MainActivity::class.java)
            val email = Email.text.toString()
            val username = Username.text.toString()
            val birthdate = Birthdate.text.toString()
            if (email == "" && username == "" && birthdate =="")
            { Toast.makeText(this, "Please enter email id, username and birthdate", Toast.LENGTH_SHORT).show()}
            else if (email == "") { Toast.makeText(this, "Please enter username", Toast.LENGTH_SHORT).show()}
            else if (username=="") {
                Toast.makeText(this, "Please enter username", Toast.LENGTH_SHORT).show()}
            else if(birthdate == ""){
                Toast.makeText(this, "Please enter birthdate", Toast.LENGTH_SHORT).show()}
            else {
                firestore_data(email,username,birthdate,intent)
//                Toast.makeText(this,"Account Created", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun firestore_data(email:String, username:String, birthdate:String,i:Intent){
        val user = hashMapOf("Birthdate" to birthdate, "Email" to email, "Username" to username)
        db.collection("users").add(user)
            .addOnSuccessListener { Toast.makeText(this,"User created",Toast.LENGTH_SHORT).show()
                startActivity(i)
                finish()
            }
            .addOnFailureListener { e -> Log.w(TAG, "Error adding the user")}
    }
}