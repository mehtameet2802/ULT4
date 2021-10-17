package com.example.ult3.fragment


import android.content.ContentValues
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.ult3.*
import com.example.ult3.R
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class Details1 : Fragment() {

    val db = Firebase.firestore
    lateinit var data1: List<videosData.Result>
    private var f: Int = 0
    val mAuth = Firebase.auth

    private val url: String = "https://api.themoviedb.org/3/movie/"
    private val b_video: String = "https://www.youtube.com/watch?v="

//    override fun onConfigurationChanged(newConfig: Configuration) {
//        super.onConfigurationChanged(newConfig)
//        if(newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
//            ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
//        }
//        else
//        {
//            ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
//        }
//    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        data1 = listOf()

        val v = inflater.inflate(R.layout.fragment_details, container, false)
        val name = arguments?.getString("mName").toString()
        val poster = arguments?.getString("mPoster").toString()
        val description = arguments?.getString("mDescription").toString()
        val rating = arguments?.getString("mRating").toString()
        val base = arguments?.getString("base").toString()
        val fav = v.findViewById<ImageButton>(R.id.fav)
        val id = arguments?.getInt("id")

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


        val rf = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RetrofitData::class.java)


//        val ApiData = rf.getData(category,api_key)
        val ApiData = rf.getTrailerData(id)

        ApiData.enqueue(object : Callback<videosData> {
            override fun onResponse(call: Call<videosData>, response: Response<videosData>) {
                data1 = response.body()?.results!!
            }

            override fun onFailure(call: Call<videosData>, t: Throwable) {
                Log.e(ContentValues.TAG, t.message.toString())
                Toast.makeText(activity, "Hello", Toast.LENGTH_SHORT).show()
            }
        })


        val data: HashSet<favDetails1> = hashSetOf()
        if(mAuth.currentUser!!.email == null ||  mAuth.currentUser!!.email == "")
        {
            val p = mAuth.currentUser!!.phoneNumber
            if (p != null) {
                db.collection("users").document(p).collection("favourites")
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
            }
        }
        else
        {
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
        }


//        db.collection("users").document(email).collection("favourites")
//            .whereEqualTo("name", name)
//            .get()
//            .addOnSuccessListener { documents ->
//                for (document in documents) {
//                    data.add(document.toObject(favDetails1::class.java))
//                    f = 1
//                    fav.setImageResource(R.drawable.favourite)
////                    Toast.makeText(activity, "1", Toast.LENGTH_SHORT).show()
//                }
//            }


        fav.setOnClickListener {
            if (f == 0) {
                val favourite1 = hashMapOf(
                    "description" to description,
                    "name" to name,
                    "poster" to poster,
                    "rating" to rating,
                )
                if(mAuth.currentUser!!.email == null ||  mAuth.currentUser!!.email == "")
                {
                    val p = mAuth.currentUser!!.phoneNumber
                    if (p != null) {
                        db.collection("users").document(p).collection("favourites").document(name)
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

                    }
                }
                else
                {
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
                }
//                db.collection("users").document(email).collection("favourites").document(name)
//                    .set(favourite1)
//                    .addOnSuccessListener {
//                        Toast.makeText(
//                            context,
//                            "Added to favourites",
//                            Toast.LENGTH_SHORT
//                        ).show()
//                    }
//                    .addOnFailureListener {
//                        Toast.makeText(
//                            context,
//                            "Some problem",
//                            Toast.LENGTH_SHORT
//                        ).show()
//                    }
                fav.setImageResource(R.drawable.favourite)
                f = 1
            } else {
                fav.setImageResource(R.drawable.favourite_not)
                f = 0
                if(mAuth.currentUser!!.email == null ||  mAuth.currentUser!!.email == "") {
                    val p = mAuth.currentUser!!.phoneNumber
                    if (p != null) {
                        db.collection("users").document(p).collection("favourites")
                            .document(name)
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
                    }
                }
            else
                {
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
                }

//                db.collection("users").document(email).collection("favourites").document(name)
//                    .delete()
//                    .addOnSuccessListener {
//                        Toast.makeText(
//                            context,
//                            "Removed from favourites",
//                            Toast.LENGTH_SHORT
//                        ).show()
//                    }
//                    .addOnFailureListener {
//                        Toast.makeText(
//                            context,
//                            "Some problem",
//                            Toast.LENGTH_SHORT
//                        ).show()
                    }
            }


        d_video.setOnClickListener() {
            if (data1.isNullOrEmpty()) {
                Toast.makeText(activity, "Sorry the trailer is not available", Toast.LENGTH_SHORT)
                    .show()
            } else {
                val key = data1.get(0).key
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(b_video + key)
                startActivity(intent)
            }
        }

        return v
    }

}
