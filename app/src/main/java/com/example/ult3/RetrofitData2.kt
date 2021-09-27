package com.example.ult3

import retrofit2.Call
import retrofit2.http.GET

interface RetrofitData2 {


//    https://api.themoviedb.org/3/movie/top_rated?api_key=a631feaba1636b38b4d07a2fb9d1ac4a&language=en-US&page=1
    @GET("top_rated?api_key=a631feaba1636b38b4d07a2fb9d1ac4a")
    fun getData(): Call<trData>
}