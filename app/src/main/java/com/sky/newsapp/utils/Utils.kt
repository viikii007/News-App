package com.sky.newsapp.utils

import com.MainActivity


class Utils {
    companion object {
        lateinit var activity: MainActivity

        var currentScreen="home"
        var search="tesla"

        const val username="username"
        const val usermail="usermail"

        val apikey="77895d2c756249b491adfb4a8d93f821"

        val sharedhelper = Sharedhelper()

    }
}