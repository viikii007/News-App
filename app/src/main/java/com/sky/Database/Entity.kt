package com.sky.Database

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "NewsTable")
data class NewsTable(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
     val content: String,
    val description: String,
    val publishedAt: String,
    val title: String,
    val url: String,
    val urlToImage: String,
    val name:String
)


@Entity
data class UserTable(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val username:String,
    val email:String,
    val password:String
)

