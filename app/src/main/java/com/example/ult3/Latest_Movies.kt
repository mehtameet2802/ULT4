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

    lateinit var email:String
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
                    adapter = Adapter1(data,email)
                }
            }

            override fun onFailure(call: Call<lmData>, t: Throwable) {
                Log.e(TAG, t.message.toString())
                Toast.makeText(activity, "Hello", Toast.LENGTH_SHORT).show()
            }
        })


    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_latest_movies, container, false)

//        Toast.makeText(context,arguments?.getString("email"),Toast.LENGTH_SHORT).show()

        val filter: FloatingActionButton = v.findViewById(R.id.filter)
        val sort: FloatingActionButton = v.findViewById(R.id.sort)



        var search_data = v.findViewById<EditText>(R.id.Search)
        val search :ImageButton = v.findViewById(R.id.searchBtn)
        search_data.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val e = mAuth.currentUser?.email
                if(s.toString()!="")
                {
                    val new_data = data.filter { lmData ->
                        (lmData.original_title).lowercase() == s.toString().lowercase()
                    }
                    rv.adapter = e?.let { Adapter1(new_data, it) }
                    rv.adapter?.notifyDataSetChanged()
                }
                if(s.toString()=="")
                {
                    rv.adapter = e?.let { Adapter1(data, it) }
                    rv.adapter?.notifyDataSetChanged()
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })


        sort.setOnClickListener {
            Collections.sort(data)
            rv.adapter?.notifyDataSetChanged()
        }





//        val bundle = arguments
//        val s = bundle?.getString("filter")
//        val f = s!!.toInt()
//        Toast.makeText(view!!.context, f, Toast.LENGTH_SHORT).show()
//        if (f != 0) {
//            filter(f)
//            Toast.makeText(context, "your_text", Toast.LENGTH_SHORT).show()
//
//        }

        filter.setOnClickListener {
            val filtered_data = data.filter{lmData -> lmData.vote_average>7}
            val e = mAuth.currentUser?.email
            rv.adapter = e?.let { it1 -> Adapter1(filtered_data, it1) }
            rv.adapter?.notifyDataSetChanged()
        }

            return v
        }

//    private fun filter(s:Int){
//        if(s==1)
//        {
//            var filtered_data = data.filter{lmData -> lmData.vote_average>7}
//            rv.adapter = Adapter1(filtered_data)
//            rv.adapter?.notifyDataSetChanged()
//        }
//        else if(s==2)
//        {
//            var filtered_data = data.filter{lmData -> lmData.vote_average>5}
//            rv.adapter = Adapter1(filtered_data)
//            rv.adapter?.notifyDataSetChanged()
//
//        }
//        else if(s==3)
//        {
//            var filtered_data = data
//            rv.adapter = Adapter1(filtered_data)
//            rv.adapter?.notifyDataSetChanged()
//        }
//    }


//    fun fun_filter(s:String){
//        val filtered_list:ArrayList<lmData.lmResult> =ArrayList()
//        for (item:lmData.lmResult in data)
//        {
//            if(item.original_title.lowercase() == s.lowercase())
//            {
//                filtered_list.add(item)
//            }
//        }
//
//       Adapter1.filterList()
//    }

//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
////        super.onCreateOptionsMenu(menu, inflater)
//        inflater.inflate(R.menu.action_bar, menu)
//
//
//        val search_btn = menu.findItem(R.id.search_icon)
//        val search = search_btn?.actionView as SearchView
//        search.queryHint = "Search Here"
//
//
//        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
//            override fun onQueryTextSubmit(query: String?): Boolean {
////                search.clearFocus()
////                search.setQuery("",false)
////                search_btn.collapseActionView()
//                return true
//            }
//
//            override fun onQueryTextChange(newText: String?): Boolean {
//
//                val e = mAuth.currentUser?.email
//                if(newText!="")
//                {
//                    val new_data = data.filter { lmData ->
//                        (lmData.original_title).lowercase() == newText?.lowercase()
//                    }
//                    rv.adapter = e?.let { Adapter1(new_data, it) }
//                    rv.adapter?.notifyDataSetChanged()
//                }
//                if(newText=="")
//                {
//                    rv.adapter = e?.let { Adapter1(data, it) }
//                    rv.adapter?.notifyDataSetChanged()
//                }
//
//                return true
//            }
//
//        } )
//
//    }


    }

