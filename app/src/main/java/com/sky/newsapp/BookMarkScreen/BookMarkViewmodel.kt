package com.sky.newsapp.BookMarkScreen

import androidx.lifecycle.ViewModel
import com.sky.navigation.AppNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BookMarkViewmodel @Inject constructor(private val appNavigator: AppNavigator) : ViewModel() {

    fun onBackPress()
    {
        appNavigator.tryNavigateBack()
    }

}