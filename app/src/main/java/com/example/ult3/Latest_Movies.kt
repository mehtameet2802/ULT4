package com.example.ult3


import android.content.ContentValues.TAG
import android.media.Image
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Adapter
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import kotlin.collections.ArrayList


class Latest_Movies : Fragment(R.layout.fragment_latest_movies) {

    //    lateinit var email:String
    lateinit var data: List<lmData.lmResult>
    lateinit var rv: RecyclerView

    val mAuth = Firebase.auth

    private val url: String = "https://api.themoviedb.org/3/movie/"
//   https://api.themoviedb.org/3/movie/popular?api_key=a631feaba1636b38b4d07a2fb9d1ac4a"


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rv = view.findViewById(R.id.rv)
        rv.apply {
            layoutManager = LinearLayoutManager(activity)
        }

        val email = arguments?.getString("email").toString()
//        Toast.makeText(context,email,Toast.LENGTH_SHORT).show()


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
                    adapter = Adapter1(data, email)
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
        val v = inflater.inflate(R.layout.fragment_latest_movies, container, false)

//        Toast.makeText(context,arguments?.getString("email"),Toast.LENGTH_SHORT).show()

        setHasOptionsMenu(true)

//        val filter: FloatingActionButton = v.findViewById(R.id.filter)
//        val sort: FloatingActionButton = v.findViewById(R.id.sort)


//        sort.setOnClickListener {
//            Collections.sort(data)
//            rv.adapter?.notifyDataSetChanged()
//        }


//        filter.setOnClickListener {
//            val filtered_data = data.filter{lmData -> lmData.vote_average>7}
//            val e = mAuth.currentUser?.email
//            rv.adapter = e?.let { it1 -> Adapter1(filtered_data, it1) }
//            rv.adapter?.notifyDataSetChanged()
//        }
            return v
        }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.action_bar, menu)


        val search_btn = menu.findItem(R.id.search_icon)
        val search = search_btn?.actionView as SearchView
        search.queryHint = "Search Here"

        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
//               search.clearFocus()
//               search.setQuery("",false)
//               search_btn.collapseActionView()
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {

                val e = mAuth.currentUser?.email.toString()
                if (newText != "")
                {
                    val new_data = data.filter { trData ->
                        (trData.original_title).lowercase() == newText?.lowercase()
                    }
                    rv.adapter = Adapter1(new_data, e)
                    rv.adapter?.notifyDataSetChanged()
                }
                if (newText == "") {
                    rv.adapter = Adapter1(data, e)
                    rv.adapter?.notifyDataSetChanged()
                }
                return true
                }

            })
        }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val e = mAuth.currentUser?.email.toString()
        return when (item.itemId) {
            R.id.a_sort -> {
                val newData = data.sortedBy { it.original_title }
                rv.adapter = Adapter1(newData, e)
                rv.adapter?.notifyDataSetChanged()
                return true
            }
            R.id.r_sort -> {
                val newData = data.sortedBy { it.vote_average }
                rv.adapter = Adapter1(newData, e)
                rv.adapter?.notifyDataSetChanged()
                return true
            }
            R.id.r_7 -> {
                val filtered_data = data.filter { lmData -> lmData.vote_average > 7 }
                rv.adapter = Adapter1(filtered_data, e)
                rv.adapter?.notifyDataSetChanged()
                return true
            }
            R.id.r_5 -> {
                val filtered_data = data.filter { lmData -> lmData.vote_average < 5 }
                rv.adapter = Adapter1(filtered_data, e)
                rv.adapter?.notifyDataSetChanged()
                return true
            }
            R.id.all -> {
                val filtered_data = data
                rv.adapter = Adapter1(filtered_data, e)
                rv.adapter?.notifyDataSetChanged()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}

