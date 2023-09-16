package com.sky.newsapp.Apiservice

import com.sky.newsapp.Apiservice.ModelClass.NewsModel
import retrofit2.Call
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NewsInterface {
    @GET("everything")
   suspend fun fetchData(
        @Query("q") query: String,
        @Query("from") fromDate: String,
        @Query("sortBy") sortBy: String,
        @Query("apiKey") apiKey: String
    ): NewsModel
}



