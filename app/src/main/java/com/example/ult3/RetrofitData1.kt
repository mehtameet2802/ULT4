package com.example.ult3

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitData1 {



    @GET("popular?api_key=a631feaba1636b38b4d07a2fb9d1ac4a")
    fun getData(): Call<lmData>


//    @GET("{category}")
//    fun getData(
//        @Path("category") category:String,
//        @Query("api_key") api_key:String
//    ): Call<lmData>

    }