package com.example.ult3

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Favourites : Fragment() {

    val mAuth  = Firebase.auth
    val db = Firebase.firestore

    lateinit var data: ArrayList<favData>

    lateinit var rv: RecyclerView
    lateinit var email:String


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rv = view.findViewById(R.id.rv)
        rv.apply {
            layoutManager = LinearLayoutManager(activity)
        }
        data = ArrayList()

        email = mAuth.currentUser?.email.toString()
        firestore_data(email,data)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_favourites, container, false)
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

                if(newText!="")
                {
                    val new_data = data.filter { favData ->
                        (favData.name)?.lowercase() == newText?.lowercase()
                    }
                    rv.adapter = Adapter_Fav(new_data)
                    rv.adapter?.notifyDataSetChanged()
                }
                if(newText=="")
                {
                    rv.adapter = Adapter_Fav(data)
                    rv.adapter?.notifyDataSetChanged()
                }

                return true
            }

        } )

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.a_sort -> {
                val newData = data.sortedBy { it.name }
                rv.adapter = Adapter_Fav(newData)
                rv.adapter?.notifyDataSetChanged()
                return true
            }
            R.id.r_sort -> {
                val newData = data.sortedBy { it.rating }
                rv.adapter = Adapter_Fav(newData)
                rv.adapter?.notifyDataSetChanged()
                return true
            }
            R.id.r_7 -> {
                val filtered_data = data.filter { favData -> favData.rating!! > 7.0.toString() }
                rv.adapter = Adapter_Fav(filtered_data)
                rv.adapter?.notifyDataSetChanged()
                return true
            }
            R.id.r_5 -> {
                val filtered_data = data.filter { favData -> favData.rating!! < 5.toString()}
                rv.adapter = Adapter_Fav(filtered_data)
                rv.adapter?.notifyDataSetChanged()
                return true
            }
            R.id.all -> {
                val filtered_data = data
                rv.adapter = Adapter_Fav(filtered_data)
                rv.adapter?.notifyDataSetChanged()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun firestore_data(email:String,data:ArrayList<favData>){
       db.collection("users").document(email).collection("favourites")
           .addSnapshotListener(object : EventListener<QuerySnapshot>{
               override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                   if(error != null){
                       Log.e("Firestore Error",error.message.toString())
                       return
                   }
                   for(dc :DocumentChange in value?.documentChanges!!)
                       if(dc.type == DocumentChange.Type.ADDED){
                           data.add(dc.document.toObject(favData::class.java))
                       }
                   rv.adapter = Adapter_Fav(data)
                   rv.adapter?.notifyDataSetChanged()
               }
           })
    }


}