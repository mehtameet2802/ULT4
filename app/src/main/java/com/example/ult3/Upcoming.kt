package com.example.ult3

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Tag


class Upcoming : Fragment() {

    val mAuth = Firebase.auth

    private val url:String="https://api.themoviedb.org/3/movie/"
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rv=view.findViewById<RecyclerView>(R.id.rv3)
        rv.apply {
            layoutManager=LinearLayoutManager(activity)
        }

        val email = mAuth.currentUser?.email.toString()


        val rf =Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RetrofitData3::class.java)

        val ApiData=rf.getData()

        ApiData.enqueue(object: Callback<ucData>
        {
            override fun onResponse(call: Call<ucData>, response: Response<ucData>) {
                val data= response.body()?.results
                rv.apply {
                    adapter=Adapter3(data,email)
                }
            }
            override fun onFailure(call: Call<ucData>, t: Throwable) {
                Log.e(TAG,t.message.toString())
            }
        }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_upcoming, container, false)
    }

}