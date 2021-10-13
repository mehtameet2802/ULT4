package com.example.ult3

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.*
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import com.google.firebase.storage.ktx.storage
import java.util.*

class activity_sign_up:AppCompatActivity() {


    var storage = Firebase.storage
    var storageRef = storage.reference
    val mAuth = Firebase.auth
    val db = Firebase.firestore
    lateinit var imageUri: Uri
    lateinit var profile: ImageView
    lateinit var Email: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        storage = FirebaseStorage.getInstance()
        storageRef = storage.reference

        Email = findViewById<EditText>(R.id.email2)
        val Password = findViewById<EditText>(R.id.password)
        val Username = findViewById<EditText>(R.id.username2)
        val Birthdate = findViewById<EditText>(R.id.birthdate)
        val sign_up = findViewById<Button>(R.id.mTrailer)
        profile = findViewById<ImageView>(R.id.sign_up)
        val set = findViewById<TextView>(R.id.save)


        set.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
//            val openGalleryIntent =
//                Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
//            startActivityForResult(openGalleryIntent, 1000)
            startActivityForResult(intent, 1000)
        }

        sign_up.setOnClickListener {
            val intent = Intent(application, MainActivity::class.java)
            val email = Email.text.toString()
            val username = Username.text.toString()
            val birthdate = Birthdate.text.toString()
            val password = Password.text.toString()


            if (email == "" && username == "" && birthdate == "" && password == "") {
                Toast.makeText(
                    this,
                    "Please enter email id, username and birthdate",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (email == "") {
                Toast.makeText(this, "Please enter username", Toast.LENGTH_SHORT).show()
            } else if (username == "") {
                Toast.makeText(this, "Please enter username", Toast.LENGTH_SHORT).show()
            } else if (birthdate == "") {
                Toast.makeText(this, "Please enter birthdate", Toast.LENGTH_SHORT).show()
            } else if (password == "") {
                Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show()
            } else {
                firestore_data(email, username, birthdate, password, intent)
                uploadpic()
            }
        }
    }

    fun firestore_data(
        email: String,
        username: String,
        birthdate: String,
        password: String,
        i: Intent
    ) {
        val user = hashMapOf(
            "Birthdate" to birthdate,
            "Email" to email,
            "Username" to username,
            "Password" to password
        )
        db.collection("users").document(email).set(user)
            .addOnSuccessListener {
                Toast.makeText(this, "User created", Toast.LENGTH_SHORT).show()
                mAuth.createUserWithEmailAndPassword(email, password)
                i.putExtra("email", email)
                startActivity(i)
                finish()
            }
            .addOnFailureListener { e -> Log.w(TAG, "Error adding the user") }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1000) {
            if (resultCode == RESULT_OK) {
                imageUri = data?.data!!
                profile.setImageURI(imageUri)
            }
        }
    }

    private fun uploadpic() {

        val image = storageRef.child(Email.toString())
        image.putFile(imageUri)

// Register observers to listen for when the download is done or if it fails
        .addOnFailureListener {
            Toast.makeText(this,"Failed toupload the image",Toast.LENGTH_SHORT).show()
            // Handle unsuccessful uploads
        }.addOnSuccessListener { taskSnapshot ->
           Toast.makeText(this,"Image has been uploaded",Toast.LENGTH_SHORT).show()
            // taskSnapshot.metadata contains file metadata such as size, content-type, etc.
            // ...
        }
    }
}
