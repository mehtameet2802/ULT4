package com.example.ult3

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Favourites : Fragment() {

    val mAuth  = Firebase.auth
    val db = Firebase.firestore

    lateinit var rv: RecyclerView
    lateinit var email:String


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rv = view.findViewById(R.id.rv)
        rv.apply {
            layoutManager = LinearLayoutManager(activity)
        }
        val data:ArrayList<favData> = ArrayList()

        email = mAuth.currentUser?.email.toString()
        firestore_data(email,data)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_favourites, container, false)
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