package com.sky.navigation

sealed class DestinationClass(protected val route: String, vararg params: String)
{
    val fullRoute: String = if (params.isEmpty()) route else {
        val builder = StringBuilder(route)
        params.forEach { builder.append("/{${it}}") }
        builder.toString()
    }

    sealed class NoArgumentsDestination(route: String) : DestinationClass(route) {
        operator fun invoke(): String = route
    }

    object SplashScreen: NoArgumentsDestination("splash_screen")

    object NewsFeed: NoArgumentsDestination("NewsFeed")

    object BookMarkScreen: NoArgumentsDestination("BookMarkScreen")

    object SearchScreen: NoArgumentsDestination("SearchScreen")

    object LoginSignup:NoArgumentsDestination("LoginSignup")

    object Profie:NoArgumentsDestination("Profie")



}

