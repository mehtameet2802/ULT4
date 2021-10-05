package com.example.ult3

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ult3.fragment.Details1


class Adapter1(private var data:List<lmData.lmResult>?):RecyclerView.Adapter<Adapter1.ViewHolder>() {

    val imageBase="https://image.tmdb.org/t/p/w500/"
    val url = "https://www.youtube.com/watch?v=x_me3xsvDgk"

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

    class ViewHolder(v: View):RecyclerView.ViewHolder(v)
    {
        var mName:TextView
//        var mDescription:TextView
        var mPoster:ImageView
        var mRating:TextView
        init{
            mName=v.findViewById(R.id.mName)
//            mDescription=v.findViewById(R.id.mDescription)
            mPoster=v.findViewById(R.id.mPoster)
            mRating=v.findViewById(R.id.imdb)

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
        f.arguments = bundle1
        return f
    }

}





