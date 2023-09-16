package com.sky.newsapp.LoginSignup

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.sky.navigation.AppNavigator
import com.sky.navigation.DestinationClass
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class LoginSignupViewModel  @Inject constructor (private val  appNavigator: AppNavigator) : ViewModel()
{

    fun onNavigatetoHome()
    {
        appNavigator.tryNavigateTo(DestinationClass.NewsFeed())
    }

    val selected_item= mutableStateOf(1)

}