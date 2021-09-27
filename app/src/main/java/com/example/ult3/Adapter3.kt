package com.example.ult3

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class Adapter3(private val data:List<ucData.ucResult>?): RecyclerView.Adapter<Adapter3.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):ViewHolder {
        val inflator=LayoutInflater.from(parent.context)
        val view =inflator.inflate(R.layout.uc_layout,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: Adapter3.ViewHolder, position: Int) {
        val input = data?.get(position)
        if (input != null) {
            holder.bind(input)
        }
    }

    override fun getItemCount(): Int {
            return data?.size!!
    }

    class ViewHolder(v:View):RecyclerView.ViewHolder(v){
        var mName:TextView
        var mDescription:TextView
        var mPoster:ImageView
        var mRating:TextView

        init{
            mName=v.findViewById(R.id.mName)
            mDescription=v.findViewById(R.id.mDescription)
            mPoster=v.findViewById(R.id.mPoster)
            mRating=v.findViewById(R.id.imdb)
        }

        val imageBase="https://image.tmdb.org/t/p/w500/"
        fun bind(ucData: ucData.ucResult)
        {
            mName.setText(ucData.original_title).toString()
            mDescription.setText(ucData.overview).toString()
            Glide.with(itemView)
                .load(imageBase+ucData.poster_path)
                .into(mPoster)
            val rating = ucData.vote_average.toString()
            mRating.setText(rating)
        }


    }

}