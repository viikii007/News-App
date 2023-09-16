package com.sky.Database

import android.app.Application
import androidx.lifecycle.AndroidViewModel


class DbViewModel(application: Application): AndroidViewModel(application) {
    private var repobj: NewsRepository

    init {
        val context = AppDatabase.getInstance(application).News_feed_dao()
        repobj = NewsRepository(context)
    }

    fun InsertNewsFeed(newsFeed: NewsTable) {
        repobj.InsertNews(newsFeed)
    }


    fun SelectBookMark():List<NewsTable>
    {
        return repobj.SelectBookMark()
    }

    fun IsBookMarked(name:String) : Boolean
    {
        return repobj.IsBookMarked(name)
    }

    fun DeleteBookMark(name: String)
    {
        return repobj.DeleteBookMark(name)
    }


}

class UserDbViewModel(application: Application): AndroidViewModel(application) {
    private var repobj: UserRepositoey

    init {
        val context = AppDatabase.getInstance(application).User_dao()
        repobj = UserRepositoey(context)
    }

    fun InsertUser(user: UserTable) {
        repobj.InsertUser(user)
    }

    fun GetUser(email:String):List<UserTable>
    {
        return repobj.GetUser(email)
    }

}