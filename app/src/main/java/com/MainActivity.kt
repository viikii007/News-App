package com

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import com.sky.Database.DbViewModel
import com.sky.Database.UserDbViewModel
import com.sky.navigation.SetNavigation
import com.sky.newsapp.Apiservice.NewsViewModel
import com.sky.newsapp.BookMarkScreen.LoginObj
import com.sky.newsapp.BookMarkScreen.newsFeedObj
import com.sky.newsapp.newsfeed.PeofileUpdateViewModelObj
import com.sky.newsapp.ui.theme.NewsAppTheme
import com.sky.newsapp.utils.Utils.Companion.activity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity()
{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewsAppTheme {
                activity=this
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    PeofileUpdateViewModelObj = ViewModelProvider(this)[NewsViewModel::class.java]

                    newsFeedObj = ViewModelProvider(this)[DbViewModel::class.java]

                    LoginObj=ViewModelProvider(this)[UserDbViewModel::class.java]

                    SetNavigation()



                }
            }
        }
    }
}

