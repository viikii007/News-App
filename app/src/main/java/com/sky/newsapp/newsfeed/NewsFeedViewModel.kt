package com.sky.newsapp.newsfeed

import androidx.lifecycle.ViewModel
import com.sky.navigation.AppNavigator
import com.sky.navigation.DestinationClass
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NewsFeedViewModel @Inject constructor(private val appNavigator: AppNavigator) : ViewModel() {

    fun onNavigatetoFavorite() {
        appNavigator.tryNavigateTo(DestinationClass.BookMarkScreen())
    }

    fun onNavigatetoNewsFeed()
    {
        appNavigator.tryNavigateTo(DestinationClass.NewsFeed())
    }

    fun onNavigatetoSearchScreen()
    {
        appNavigator.tryNavigateTo(DestinationClass.SearchScreen())
    }
    fun onNavigatetoProfile()
    {
        appNavigator.tryNavigateTo(DestinationClass.Profie())
    }

}