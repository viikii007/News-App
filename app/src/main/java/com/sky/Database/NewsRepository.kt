package com.sky.Database




class NewsRepository(var daoobj:NewsDao)
{
    fun InsertNews(newsFeed: NewsTable) {
        daoobj.InsertNewsFeed(newsFeed)
    }

    fun SelectBookMark():List<NewsTable>
    {
        return daoobj.SelectBookMarek()
    }


    fun IsBookMarked(name:String) : Boolean
    {
        return daoobj.IsBookMarked(name)
    }

    fun DeleteBookMark(name: String)
    {
         return daoobj.DeleteBookMark(name)
    }
}

class UserRepositoey(var daoobj:UserDao)
{
    fun InsertUser(user: UserTable) {
        daoobj.InsertUser(user)
    }

    fun GetUser(email:String):List<UserTable>
    {
        return daoobj.getUser(email)
    }

}
