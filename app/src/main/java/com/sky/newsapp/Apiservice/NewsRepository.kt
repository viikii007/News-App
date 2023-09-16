package com.sky.newsapp.Apiservice

import android.util.Log
import com.sky.newsapp.Apiservice.ModelClass.NewsModel
import com.sky.newsapp.Apiservice.NewsInterface
import retrofit2.Call

class NewsRepository {

        var apiobj : NewsInterface = MainApiService.RetrofitService()
//
//    fun detail( q : String , from :String,sortby:String,apiKey:String ) : Call<NewsModel>
//        {
//            return apiobj.fetchData(q, from, sortby, apiKey)
//        }


    suspend  fun GetDetails(q : String , from :String,sortby:String,apiKey:String ):DataOrException<NewsModel,Boolean,Exception>
    {
        val response=try {
            apiobj.fetchData(q, from, sortby, apiKey)
        }
        catch (e:Exception) {
            Log.d("REX", "getProfile: $e")
            return DataOrException(e=e)
        }
        Log.d("REX", "getprofilerespoce: $response")
        return DataOrException(data = response)
    }






    }


class DataOrException<T, Boolean, E: Exception>(
    var data: T? = null,
    var loading: kotlin.Boolean? = null,
    var e: E? = null)