package com.example.ult3

import retrofit2.Call
import retrofit2.http.GET

interface RetrofitData3 {


//    https://api.themoviedb.org/3/movie/upcoming?api_key=a631feaba1636b38b4d07a2fb9d1ac4a
    @GET("upcoming?api_key=a631feaba1636b38b4d07a2fb9d1ac4a")
    fun getData(): Call<ucData>
}