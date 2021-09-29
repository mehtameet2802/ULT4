package com.example.ult3


import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.max


class Latest_Movies : Fragment() {

    lateinit var data: List<lmData.lmResult>
    lateinit var rv: RecyclerView

    //    private val category:String="popular"
//    private val api_key: String ="?api_key=a631feaba1636b38b4d07a2fb9d1ac4a"
    private val url: String = "https://api.themoviedb.org/3/movie/"
//   https://api.themoviedb.org/3/movie/popular?api_key=a631feaba1636b38b4d07a2fb9d1ac4a"


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        rv = view.findViewById<RecyclerView>(R.id.rv)
        rv.apply {
            layoutManager = LinearLayoutManager(activity)
        }

        val rf = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RetrofitData1::class.java)


//        val ApiData = rf.getData(category,api_key)
        val ApiData = rf.getData()

        ApiData.enqueue(object : Callback<lmData> {
            override fun onResponse(call: Call<lmData>, response: Response<lmData>) {
                data = response.body()?.results!!
                rv.apply {
                    adapter = Adapter1(data)
                }
            }

            override fun onFailure(call: Call<lmData>, t: Throwable) {
                Log.e(TAG, t.message.toString())
                Toast.makeText(activity, "Hello", Toast.LENGTH_SHORT).show()
            }
        })


    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_latest_movies, container, false)
        val filter: FloatingActionButton = view.findViewById(R.id.filter)
        val sort: FloatingActionButton = view.findViewById(R.id.sort)

        sort.setOnClickListener {
            Collections.sort(data)
            rv.adapter?.notifyDataSetChanged()
        }


//        filter1.setOnClickListener {
//            rv.adapter = Adapter1(data)
//            rv.adapter?.notifyDataSetChanged()
//        }

        filter.setOnClickListener {
            var filtered_data = data.filter{lmData -> lmData.vote_average>7}
            rv.adapter = Adapter1(filtered_data)
            rv.adapter?.notifyDataSetChanged()
        }

            return view
        }

    }

