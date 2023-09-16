package com.sky.newsapp

import androidx.lifecycle.ViewModel
import com.sky.navigation.AppNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class MainViewModel  @Inject constructor (appNavigator: AppNavigator) : ViewModel()
{
    val navigationChannel = appNavigator.navigationChannel
}