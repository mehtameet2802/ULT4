package com.example.ult3

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ult3.fragment.Details1
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class Adapter1(private var data:List<lmData.lmResult>?,var email:String):RecyclerView.Adapter<Adapter1.ViewHolder>() {

    val imageBase="https://image.tmdb.org/t/p/w500/"
    val url = "https://www.youtube.com/watch?v=x_me3xsvDgk"

    val mAuth = Firebase.auth
    val db = Firebase.firestore


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater= LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.lm_layout,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val input = data?.get(position)
        if (input != null) {
            holder.bind(input)
        }


//        holder.fav.setOnClickListener { v ->
////            var f = 0
////            val n = input?.original_title
//            val activity = v.context as AppCompatActivity
//            val i = favourites(position)
//            if (i == 1) {
//                Toast.makeText(activity, "Added to favourites", Toast.LENGTH_SHORT).show()
//            }

//            val get_data = n?.let {
//                db.collection("users").document(email).collection("favourites").document(it)
//            }
//            get_data?.get()
//                ?.addOnSuccessListener { document ->
//                    if (document != null) {
//                        f = 1
//                    } else {
//                        Log.d(TAG, "No such document")
//                    }
//                }
//                ?.addOnFailureListener { exception ->
//                    Log.d(TAG, "get failed with ", exception)
//                }

        holder.itemView.setOnClickListener { v ->
            val activity = v.context as AppCompatActivity
            val fragment = newInstance(position)
            if (fragment != null) {
                activity.supportFragmentManager.beginTransaction().replace(R.id.frame_layout,fragment).addToBackStack(null).commit()
            }
        }
    }

    override fun getItemCount(): Int {
        return data?.size!!
    }

//    fun filterList(filteredList:ArrayList<lmData.lmResult>) {
//        data = filteredList
//        notifyDataSetChanged()
//    }

    class ViewHolder(v: View):RecyclerView.ViewHolder(v)
    {
        var mName:TextView
//        var mDescription:TextView
        var mPoster:ImageView
        var mRating:TextView
//        val fav:ImageButton
        init{
            mName=v.findViewById(R.id.mName)
//            mDescription=v.findViewById(R.id.mDescription)
            mPoster=v.findViewById(R.id.mPoster)
            mRating=v.findViewById(R.id.imdb)
//            fav = v.findViewById(R.id.fav)

        }

        private val imageBase="https://image.tmdb.org/t/p/w500/"
        fun bind(lmData:lmData.lmResult)
        {
            mName.setText(lmData.original_title).toString()
//            mDescription.setText(lmData.overview).toString()
            Glide.with(itemView)
                .load(imageBase+lmData.poster_path)
                .into(mPoster)
            val rating = lmData.vote_average.toString()
            mRating.setText(rating)
        }
    }


    fun newInstance(index: Int):Fragment? {
        val f = Details1()
        val bundle1 = Bundle()
        bundle1.putString("mName",data?.get(index)?.original_title)
        bundle1.putString("mDescription",data?.get(index)?.overview)
        bundle1.putString("mRating", data?.get(index)?.vote_average.toString())
        bundle1.putString("mPoster",data?.get(index)?.poster_path)
        bundle1.putString("base",imageBase)
        bundle1.putString("mUrl",url)
        bundle1.putString("email",email)
        f.arguments = bundle1
        return f
    }

//    fun favourites(index:Int):Int{
//        val des = data?.get(index)?.overview.toString()
//        val name = data?.get(index)?.original_title.toString()
//        val poster = data?.get(index)?.poster_path.toString()
//        val rating = data?.get(index)?.vote_average.toString()
//        val favourite = hashMapOf(
//            "description" to des,
//            "name" to name,
//            "poster" to poster,
//            "rating" to rating,
////            "fav" to 1
//        )
//        db.collection("users").document(email).collection("favourites").document(name).set(favourite)
//        return 1
//    }
}





