package com.example.ult3

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitData {

    @GET("popular?api_key=a631feaba1636b38b4d07a2fb9d1ac4a&page=")
    fun getLatestData(
        @Query("page") page:Int
    ): Call<lmData>


    //    https://api.themoviedb.org/3/movie/top_rated?api_key=a631feaba1636b38b4d07a2fb9d1ac4a&language=en-US&page=1
    @GET("top_rated?api_key=a631feaba1636b38b4d07a2fb9d1ac4a&page=")
    fun getTop_RatedData(
        @Query("page") page:Int
    ): Call<trData>


    //    https://api.themoviedb.org/3/movie/upcoming?api_key=a631feaba1636b38b4d07a2fb9d1ac4a
    @GET("upcoming?api_key=a631feaba1636b38b4d07a2fb9d1ac4a&page=")
    fun getUpcomingData(
        @Query("page") page:Int?
    ): Call<ucData>


    //    https://api.themoviedb.org/3/movie/{movie_id}/videos?api_key=a631feaba1636b38b4d07a2fb9d1ac4a&language=en-US&page=1
    @GET("{movie_id}/videos?api_key=a631feaba1636b38b4d07a2fb9d1ac4a")
    fun getTrailerData(
        @Path("movie_id") id:Int?
    ): Call<videosData>
    }