package com.example.ult3

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory



class Upcoming : Fragment() {

//    lateinit var email:String
    lateinit var data: List<ucData.ucResult>
    lateinit var rv: RecyclerView


    val mAuth = Firebase.auth

    private val url:String="https://api.themoviedb.org/3/movie/"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        data = listOf()

        rv=view.findViewById(R.id.rv3)
        rv.apply{
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
                data = response.body()?.results!!
                rv.apply {
                    adapter = Adapter3(data,email)
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

        val v =  inflater.inflate(R.layout.fragment_upcoming, container, false)
        setHasOptionsMenu(true)
        return v
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.action_bar, menu)


        val search_btn = menu.findItem(R.id.search_icon)
        val search = search_btn?.actionView as SearchView
        search.queryHint = "Search Here"


        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
//                search.clearFocus()
//                search.setQuery("",false)
//                search_btn.collapseActionView()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                val e = mAuth.currentUser?.email.toString()
                if(newText!="")
                {
                    val new_data = data.filter { ucData ->
                        (ucData.original_title).lowercase() == newText?.lowercase()
                    }
                    rv.adapter = Adapter3(new_data, e)
                    rv.adapter?.notifyDataSetChanged()
                }
                if(newText=="")
                {
                    rv.adapter = Adapter3(data, e)
                    rv.adapter?.notifyDataSetChanged()
                }

                return true
            }

        } )

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val e = mAuth.currentUser?.email.toString()
        return when (item.itemId) {
            R.id.a_sort -> {
                val newData = data.sortedBy { it.original_title }
                rv.adapter = Adapter3(newData, e)
                rv.adapter?.notifyDataSetChanged()
                return true
            }
            R.id.r_sort -> {
                val newData = data.sortedBy { it.vote_average }
                rv.adapter = Adapter3(newData, e)
                rv.adapter?.notifyDataSetChanged()
                return true
            }
            R.id.r_7 -> {
                val filtered_data = data.filter { lmData -> lmData.vote_average > 7 }
                rv.adapter = Adapter3(filtered_data, e)
                rv.adapter?.notifyDataSetChanged()
                return true
            }
            R.id.r_5 -> {
                val filtered_data = data.filter { lmData -> lmData.vote_average < 5 }
                rv.adapter = Adapter3(filtered_data, e)
                rv.adapter?.notifyDataSetChanged()
                return true
            }
            R.id.all -> {
                val filtered_data = data
                rv.adapter = Adapter3(filtered_data, e)
                rv.adapter?.notifyDataSetChanged()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
