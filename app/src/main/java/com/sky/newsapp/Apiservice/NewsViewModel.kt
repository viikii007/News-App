package com.sky.newsapp.Apiservice

import androidx.lifecycle.ViewModel
import com.sky.newsapp.Apiservice.ModelClass.NewsModel

class NewsViewModel : ViewModel(){

    var repositoryObj: NewsRepository? = null

    init {
        repositoryObj = NewsRepository()
    }

//      fun GetNews(): DataOrException<NewsModel, Boolean, Exception>?
//    {
//        return repositoryObj?.GetNews()
//    }



    suspend  fun GetNews(q : String , from :String,sortby:String,apiKey:String): DataOrException<NewsModel, Boolean, Exception>?
    {
        return repositoryObj?.GetDetails(q, from, sortby, apiKey)
    }
}

