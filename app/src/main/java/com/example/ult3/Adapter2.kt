package com.example.ult3

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class Adapter2(private var data:List<trData.trResult>?):RecyclerView.Adapter<Adapter2.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):ViewHolder {
        val inflator=LayoutInflater.from(parent.context)
        val view=inflator.inflate(R.layout.tr_layout,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder:ViewHolder, position: Int) {
        val input= data?.get(position)
        if (input != null) {
            holder.bind(input)
        }
    }

    override fun getItemCount(): Int {
        return data?.size!!
    }

    class ViewHolder(v: View):RecyclerView.ViewHolder(v) {


        val mName:TextView
        val mDescription:TextView
        val mPoster:ImageView
        var mRating:TextView

        init{
            mName=v.findViewById(R.id.mName)
            mDescription=v.findViewById(R.id.mDescription)
            mPoster=v.findViewById(R.id.mPoster)
            mRating=v.findViewById(R.id.imdb)

        }

        val imageBase="https://image.tmdb.org/t/p/w500/"
        fun bind(trData: trData.trResult)
        {
            mName.setText(trData.original_title).toString()
            mDescription.setText(trData.overview).toString()
            Glide.with(itemView)
                .load(imageBase+ trData.poster_path)
                .into(mPoster)
            val rating = trData.vote_average.toString()
            mRating.setText(rating)
        }
    }
}