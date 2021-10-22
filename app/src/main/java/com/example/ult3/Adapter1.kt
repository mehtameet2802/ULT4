package com.example.ult3

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ult3.fragment.Details1
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class Adapter1(private var data: List<lmData.lmResult>, var email:String):RecyclerView.Adapter<Adapter1.ViewHolder>() {

    val imageBase="https://image.tmdb.org/t/p/w500/"
    val mAuth = Firebase.auth


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater= LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.tr_layout,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val input = data[position]
        if (input != null) {
            holder.bind(input)
        }


        holder.itemView.setOnClickListener { v ->
            val activity = v.context as AppCompatActivity
            val fragment = newInstance(position)
            if (fragment != null) {
                activity.supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.frame_layout,fragment)
                    .addToBackStack(null)
                    .commit()
            }
        }

    }

    override fun getItemCount(): Int {
        return data.size
    }


    class ViewHolder(v: View):RecyclerView.ViewHolder(v)
    {
        var mName:TextView = v.findViewById(R.id.mName)
        var mDescription:TextView = v.findViewById(R.id.mDescription)
        var mPoster:ImageView = v.findViewById(R.id.mPoster)
        var mRating:TextView = v.findViewById(R.id.imdb)

        private val imageBase="https://image.tmdb.org/t/p/w500/"
        fun bind(lmData:lmData.lmResult)
        {
            mName.setText(lmData.original_title).toString()
            mDescription.setText(lmData.overview).toString()
            Glide.with(itemView)
                .load(imageBase+lmData.poster_path)
                .into(mPoster)
            val rating = lmData.vote_average.toString()
            mRating.text = rating
        }
    }


    private fun newInstance(index: Int): Fragment {
        val f = Details1()
        val bundle1 = Bundle()
        bundle1.putString("mName", data[index].original_title)
        bundle1.putString("mDescription", data[index].overview)
        bundle1.putString("mRating", data[index].vote_average.toString())
        bundle1.putString("mPoster", data[index].poster_path)
        bundle1.putString("base",imageBase)
        bundle1.putString("email",email)
        data[index].id.let { bundle1.putInt("id", it) }
        f.arguments = bundle1
        return f
    }

}





