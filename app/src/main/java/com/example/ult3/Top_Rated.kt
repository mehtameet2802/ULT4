package com.example.ult3

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.AbsListView
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class Top_Rated : Fragment() {

    var page:Int = 1
    val limit:Int = 261

//    lateinit var email:String
    lateinit var data: ArrayList<trData.trResult>
    lateinit var rv: RecyclerView

    val mAuth = Firebase.auth


    private val url:String="https://api.themoviedb.org/3/movie/"
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        page = 1
        var s:Boolean = false
        data = ArrayList()

        rv = view.findViewById<RecyclerView>(R.id.rv)
        rv.apply {
            layoutManager = LinearLayoutManager(activity)
        }

        val email = mAuth.currentUser?.email.toString()

        val rf = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RetrofitData::class.java)

        val ApiData = rf.getTop_RatedData(page)

        ApiData.enqueue(object : Callback<trData> {
            override fun onResponse(call: Call<trData>, response: Response<trData>) {
                data = (response.body()?.results as ArrayList<trData.trResult>?)!!
                rv.apply{
                    adapter = Adapter2(data,email)
                }
            }
            override fun onFailure(call: Call<trData>, t: Throwable) {
                Log.e(TAG,t.message.toString())
                Toast.makeText(activity,"Hello", Toast.LENGTH_SHORT).show()
            }
        })


        rv.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                    s = true
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val currentItems = LinearLayoutManager(activity).childCount
                val totalItems = LinearLayoutManager(activity).itemCount
                val scrolledItems = LinearLayoutManager(activity).findFirstCompletelyVisibleItemPosition()

                if (s && page<=limit) {
                    s = false
                    page += 1
                    val apiData = rf.getTop_RatedData(page)

                    apiData.enqueue(object : Callback<trData> {
                        override fun onResponse(
                            call: Call<trData>,
                            response: Response<trData>
                        ) {
                            val data1 = response.body()?.results!!
                            data.addAll(data1)
                            rv.adapter?.notifyDataSetChanged()
                        }

                        override fun onFailure(call: Call<trData>, t: Throwable) {
                                Log.e(TAG, t.message.toString())
                                Toast.makeText(activity, "Hello", Toast.LENGTH_SHORT).show()
                        }
                    })
                }
            }
        })


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_latest_movies, container, false)
        val navbar = requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation)
        navbar.visibility = View.VISIBLE
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
                    val new_data = data.filter { trData ->
//                        (trData.original_title).lowercase() == newText?.lowercase()
                        val s = (trData.original_title).lowercase()
                        newText!!.lowercase().let { s.startsWith(it) }
                    }
                    rv.adapter = Adapter2(new_data, e)
                    rv.adapter?.notifyDataSetChanged()
                }
                if(newText=="")
                {
                    rv.adapter = Adapter2(data, e)
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
                rv.adapter = Adapter2(newData, e)
                rv.adapter?.notifyDataSetChanged()
                return true
            }
            R.id.r_sort -> {
                val newData = data.sortedBy { it.vote_average }
                rv.adapter = Adapter2(newData, e)
                rv.adapter?.notifyDataSetChanged()
                return true
            }
            R.id.r_7 -> {
                val filtered_data = data.filter { lmData -> lmData.vote_average > 7 }
                rv.adapter = Adapter2(filtered_data, e)
                rv.adapter?.notifyDataSetChanged()
                return true
            }
            R.id.r_5 -> {
                val filtered_data = data.filter { lmData -> lmData.vote_average < 5 }
                rv.adapter = Adapter2(filtered_data, e)
                rv.adapter?.notifyDataSetChanged()
                return true
            }
            R.id.all -> {
                val filtered_data = data
                rv.adapter = Adapter2(filtered_data, e)
                rv.adapter?.notifyDataSetChanged()
                return true
            }
            R.id.account ->{
                val display = activity?.supportFragmentManager!!.beginTransaction()
                display.replace(R.id.frame_layout, profile()).addToBackStack("profile").commit()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
