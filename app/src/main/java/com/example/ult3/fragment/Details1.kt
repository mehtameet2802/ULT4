package com.example.ult3.fragment


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.ult3.R
import com.example.ult3.favDetails1
import com.google.firebase.firestore.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class Details1 : Fragment() {

    val db = Firebase.firestore
    private var f: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_details1, container, false)
        val name = arguments?.getString("mName").toString()
        val poster = arguments?.getString("mPoster").toString()
        val description = arguments?.getString("mDescription").toString()
        val rating = arguments?.getString("mRating").toString()
        val url = arguments?.getString("mUrl").toString()
        val base = arguments?.getString("base").toString()
        val fav = v.findViewById<ImageButton>(R.id.fav)

        val email = arguments?.getString("email").toString()

        val d_Name: TextView = v.findViewById(R.id.d_Name)
        d_Name.text = name
        val d_Description: TextView = v.findViewById(R.id.d_Description)
        d_Description.text = description
        val d_Poster: ImageView = v.findViewById(R.id.d_Poster)
        Glide.with(d_Poster).load(base + poster).into(d_Poster)
        val d_Rating: TextView = v.findViewById(R.id.d_Rating)
        d_Rating.text = rating
        val d_video: Button = v.findViewById(R.id.d_Button)


        val data: HashSet<favDetails1> = hashSetOf()

        db.collection("users").document(email).collection("favourites")
            .whereEqualTo("name", name)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    data.add(document.toObject(favDetails1::class.java))
                    f = 1
                    fav.setImageResource(R.drawable.favourite)
//                    Toast.makeText(activity, "1", Toast.LENGTH_SHORT).show()
                }
            }


//        val a = f.toString()
//        Toast.makeText(activity, a, Toast.LENGTH_SHORT).show()

//        if (f == 1) {
//            fav.setImageResource(R.drawable.favourite)
//        } else if (f == 0) {
//            fav.setImageResource(R.drawable.favourite_not)
//        }

        fav.setOnClickListener {
            if (f == 0) {
                val favourite1 = hashMapOf(
                    "description" to description,
                    "name" to name,
                    "poster" to poster,
                    "rating" to rating,
                )
                db.collection("users").document(email).collection("favourites").document(name)
                    .set(favourite1)
                    .addOnSuccessListener {
                        Toast.makeText(
                            context,
                            "Added to favourites",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    .addOnFailureListener {
                        Toast.makeText(
                            context,
                            "Some problem",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                fav.setImageResource(R.drawable.favourite)
                f = 1
            } else {
                fav.setImageResource(R.drawable.favourite_not)
                db.collection("users").document(email).collection("favourites").document(name)
                    .delete()
                    .addOnSuccessListener {
                        Toast.makeText(
                            context,
                            "Removed from favourites",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    .addOnFailureListener {
                        Toast.makeText(
                            context,
                            "Some problem",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                f = 0
//                    .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully deleted!") }
//                    .addOnFailureListener { e -> Log.w(TAG, "Error deleting document", e) }
            }
        }

        d_video.setOnClickListener() {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
        }


        return v
    }
}
