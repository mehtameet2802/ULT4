package com.example.ult3

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class Adapter_Fav(private var data:ArrayList<favData>): RecyclerView.Adapter<Adapter_Fav.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.tr_layout,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val input = data.get(position)
        holder.bind(input)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class ViewHolder(v: View):RecyclerView.ViewHolder(v){
        val mName:TextView = v.findViewById(R.id.mName)
        private val mDescription:TextView = v.findViewById(R.id.mDescription)
        private val mPoster:ImageView = v.findViewById(R.id.mPoster)
        private val mRating:TextView = v.findViewById(R.id.imdb)

        private val imageBase="https://image.tmdb.org/t/p/w500/"

        fun bind(fvData:favData) {
            mName.setText(fvData.name).toString()
            mDescription.setText(fvData.description).toString()
            Glide.with(itemView)
                .load(imageBase+fvData.poster)
                .into(mPoster)
            mRating.setText(fvData.rating).toString()
        }
    }
}