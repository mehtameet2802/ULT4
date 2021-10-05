package com.example.ult3.fragment


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.ult3.R


class Details1 : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_details1, container, false)
        val name = arguments?.getString("mName").toString()
        val poster = arguments?.getString("mPoster").toString()
        val description = arguments?.getString("mDescription").toString()
        val rating = arguments?.getString("mRating").toString()
        val url = arguments?.getString("mUrl").toString()
        val base = arguments?.getString("base").toString()

        val d_Name: TextView = v.findViewById(R.id.d_Name)
        d_Name.text = name
        val d_Description:TextView=v.findViewById(R.id.d_Description)
        d_Description.text = description
        val d_Poster: ImageView =v.findViewById(R.id.d_Poster)
        Glide.with(d_Poster).load(base+poster).into(d_Poster)
        val d_Rating:TextView=v.findViewById(R.id.d_Rating)
        d_Rating.text = rating
        val d_video:Button = v.findViewById(R.id.d_Button)
        d_video.setOnClickListener() {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
        }
        return v
    }



}