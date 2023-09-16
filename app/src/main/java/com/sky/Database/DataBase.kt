package com.sky.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [NewsTable::class,UserTable::class],
    version =1,
    exportSchema = false
)

abstract class AppDatabase : RoomDatabase()
{
    abstract fun News_feed_dao(): NewsDao
    abstract fun User_dao():UserDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null
        private var DATABASE_NAME: String = "newsapp"

        fun getInstance(context: Context): AppDatabase {

            if (INSTANCE == null) {
                INSTANCE =
                    Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                        .allowMainThreadQueries()
                        .build()
            }
            return INSTANCE!!
        }

    }

}