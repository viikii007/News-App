package com.sky.Database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface NewsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun InsertNewsFeed(newsFeed: NewsTable)


    @Query("SELECT * FROM NewsTable ")
    fun SelectBookMarek():List<NewsTable>

    @Query("SELECT * FROM NewsTable where name=:name")
    fun IsBookMarked(name:String) : Boolean

    @Query("DELETE FROM newstable WHERE name=:name")
    fun DeleteBookMark(name: String)
}


@Dao
interface UserDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun InsertUser(usertable: UserTable)

    @Query("SELECT * FROM UserTable where email=:email")
    fun getUser(email:String):List<UserTable>

}