package com.sky.newsapp.splashScreen

import androidx.lifecycle.ViewModel
import com.sky.navigation.AppNavigator
import com.sky.navigation.DestinationClass
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject



@HiltViewModel
class SplashViewModel  @Inject constructor(private val appNavigator: AppNavigator) : ViewModel()
{

    fun onNavigatetoHomeScreen() {
        appNavigator.tryNavigateTo(DestinationClass.NewsFeed())
    }

    fun onNavigatetoLogin()
    {
        appNavigator.tryNavigateTo(DestinationClass.LoginSignup())
    }


}