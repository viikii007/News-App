package com.sky.newsapp.searchScreen

import androidx.lifecycle.ViewModel
import com.sky.navigation.AppNavigator
import com.sky.navigation.DestinationClass
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel  @Inject constructor(private val appNavigator: AppNavigator) : ViewModel()
{

    fun onBackPress()
    {
        appNavigator.tryNavigateBack()
    }
}