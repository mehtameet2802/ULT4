package com.example.ult3

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class profile : Fragment() {


    var url: String = ""
    val mAuth = Firebase.auth
    val db = Firebase.firestore
    private lateinit var mGoogleSignInClient: GoogleSignInClient

//    override fun onStart() {
//        super.onStart()
//
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_profile, container, false)
        val email = v.findViewById<EditText>(R.id.emailText)
        val num = v.findViewById<EditText>(R.id.numText)
        val user = v.findViewById<EditText>(R.id.userText)
        val birth = v.findViewById<EditText>(R.id.bithText)
        val update = v.findViewById<Button>(R.id.update)
//        val profile = v.findViewById<ImageView>(R.id.profile)


//        val p = mAuth.currentUser!!.phoneNumber
//        num.setText(p)

        if (mAuth.currentUser!!.email == "" || mAuth.currentUser!!.email == null) {
            val p = mAuth.currentUser!!.phoneNumber
            num.setText(p)
            if (p != null) {
                db.collection("users").document(p)
                    .get()
                    .addOnSuccessListener {
                        email.setText(it.getString("Email"))
                        user.setText(it.getString("Username"))
                        birth.setText(it.getString("Birthdate"))
        //                url = it.getString("iurl").toString()
        //                if(url!="")
        //                {
        //                    Glide.with(this)
        //                        .l
        //                        .using(FirebaseImageLoader())
        //                        .load(storageReference)
        //                        .into(imageView)
        //                }
                    }
                    .addOnFailureListener {
                        email.setText("")
                        user.setText("")
                        birth.setText("")
//                        Toast.makeText(activity, "Signed in using mobile", Toast.LENGTH_SHORT).show()
                    }
            }

        } else {
            val e = mAuth.currentUser!!.email
            db.collection("users").document(e.toString())
                .get()
                .addOnSuccessListener {
                    email.setText(it.getString("Email"))
                    user.setText(it.getString("Username"))
                    birth.setText(it.getString("Birthdate"))
                    num.setText(it.getString("number"))
//                url = it.getString("iurl").toString()
//                if(url!="")
//                {
//                    Glide.with(this)
//                        .l
//                        .using(FirebaseImageLoader())
//                        .load(storageReference)
//                        .into(imageView)
//                }
                }
                .addOnFailureListener {

                }
        }



        update.setOnClickListener {

            if (email.toString() == "" && user.toString() == "" && birth.toString() == "" && num.toString() == "") {
                Toast.makeText(
                    activity,
                    "Please enter email id, username, birthdate and number",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (email.toString() == "") {
                Toast.makeText(activity, "Please enter username", Toast.LENGTH_SHORT).show()
            } else if (user.toString() == "") {
                Toast.makeText(activity, "Please enter username", Toast.LENGTH_SHORT).show()
            } else if (birth.toString() == "") {
                Toast.makeText(activity, "Please enter birthdate", Toast.LENGTH_SHORT).show()
            } else if (num.toString() == "") {
                Toast.makeText(activity, "Please enter number", Toast.LENGTH_SHORT).show()
            } else {
                firestore_data(email.text.toString(), user.text.toString(), birth.text.toString(),num.text.toString())
//                if (mAuth.currentUser!!.email == null || mAuth.currentUser!!.email == "") {
//                    firestore_data2(email.text.toString(), user.text.toString(), birth.text.toString(),num.text.toString())
//                } else {
//                    firestore_data1(num.toString())
//                }
            }
        }


        val lg_btn = v.findViewById<Button>(R.id.lg_btn)
        lg_btn.setOnClickListener {
            val builder = activity?.let { it1 -> AlertDialog.Builder(it1) }
            builder!!.setTitle("Exit/Logout")
            builder.setMessage("Do you really want to exit \n You will be logged out")
            builder.setPositiveButton("Yes") { dialog, which ->
                signOut()
                val intent = Intent(activity, LoginActivity::class.java)
                startActivity(intent)
                activity?.finish()
            }
            builder.setNegativeButton("No") { dialog, which ->
                Toast.makeText(activity, "Thank you for staying", Toast.LENGTH_SHORT).show()
//            val intent = Intent(this,SplashScreen::class.java)
//            startActivity(intent)
//            finish()
            }
            builder.setNeutralButton("Cancel") { dialog, which ->
                Toast.makeText(activity, "Thank you for staying", Toast.LENGTH_SHORT).show()
            }

            val dialog: AlertDialog = builder.create()
            dialog.show()
        }
        return v
    }


    private fun signOut() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        Toast.makeText(activity, "You have been successfully signed out", Toast.LENGTH_SHORT).show()
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(activity, gso)
        mGoogleSignInClient.signOut()

        Firebase.auth.signOut()
    }

    fun firestore_data(email: String, username: String, birthdate: String, num:String)
    {
        val data = hashMapOf(
            "Birthdate" to birthdate,
            "Email" to email,
            "Username" to username,
            "number" to num
        )
        if (mAuth.currentUser!!.email == null || mAuth.currentUser!!.email == "") {
            mAuth.currentUser!!.phoneNumber?.let {
                db.collection("users").document(it)
                    .set(data)
//                    .update(mapOf("Birthdate" to birthdate, "Email" to email, "Username" to username, "number" to num))
                    .addOnSuccessListener {
                        Toast.makeText(activity, "Data updated successfully", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener { e -> Log.w(ContentValues.TAG, "Error adding the user") }
            }
        }
        else{
            mAuth.currentUser!!.email?.let {
                db.collection("users").document(it)
                    .update(mapOf("Birthdate" to birthdate, "Email" to email, "Username" to username, "number" to num))
                    .addOnSuccessListener {
                        Toast.makeText(activity, "Data updated successfully", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener { e -> Log.w(ContentValues.TAG, "Error adding the user") }
            }
        }
        }

    }

//    fun firestore_data1(num:String){
//        val data = hashMapOf(
//            "number" to num
//        )
//        mAuth.currentUser!!.email?.let {
//            db.collection("users").document(it)
//                .set(data)
//                .addOnSuccessListener { Toast.makeText(activity, "Data updated successfully", Toast.LENGTH_SHORT).show() }
//                .addOnFailureListener { Toast.makeText(activity,"Some error occurred while updating the data",Toast.LENGTH_SHORT).show()}
//        }
//    }

//    fun firestore_data2(
//        email: String,
//        username: String,
//        birthdate: String,
//        num:String
//    ) {
//        val user = hashMapOf(
//            "Birthdate" to birthdate,
//            "Email" to email,
//            "Username" to username,
//            "number" to num
//        )
//        mAuth.currentUser!!.phoneNumber?.let {
//            db.collection("users").document(it)
//                .set(user)
//                .addOnSuccessListener {
//    //                Toast.makeText(this, "Please login", Toast.LENGTH_SHORT).show()
//                    Toast.makeText(activity, "Data updated successfully", Toast.LENGTH_SHORT).show()
//                }
//                .addOnFailureListener { e -> Log.w(ContentValues.TAG, "Error adding the user") }
//        }
//    }


