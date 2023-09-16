package com.sky.newsapp.profileScreen

import androidx.lifecycle.ViewModel
import com.sky.navigation.AppNavigator
import com.sky.navigation.DestinationClass
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject



@HiltViewModel
class ProfileViewModel @Inject constructor(private val appNavigator: AppNavigator) : ViewModel() {

    fun onNavigatetoLogin()
    {
        appNavigator.tryNavigateTo(DestinationClass.LoginSignup())
    }
    fun onBackPress()
    {
        appNavigator.tryNavigateBack()
    }

}