package com.example.ult3

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class Top_Rated : Fragment() {

    val mAuth = Firebase.auth

    private val url:String="https://api.themoviedb.org/3/movie/"
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rv = view.findViewById<RecyclerView>(R.id.rv2)
        rv.apply {
            layoutManager = LinearLayoutManager(activity)
        }

        val email = mAuth.currentUser?.email.toString()
//        val email = arguments?.getString("email").toString()


        val rf = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RetrofitData2::class.java)

        val ApiData = rf.getData()

        ApiData.enqueue(object : Callback<trData> {
            override fun onResponse(call: Call<trData>, response: Response<trData>) {
                val data= response.body()?.results
                rv.apply{
                    adapter=Adapter2(data,email)
                }
            }
            override fun onFailure(call: Call<trData>, t: Throwable) {
                Log.e(TAG,t.message.toString())
                Toast.makeText(activity,"Hello", Toast.LENGTH_SHORT).show()
            }
        })


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_top_rated, container, false)
    }


}