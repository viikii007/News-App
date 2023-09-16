package com.sky.newsapp.BookMarkScreen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sky.Database.DbViewModel
import com.sky.Database.UserDbViewModel
import com.sky.Database.UserTable
import com.sky.newsapp.detailScreen.NewsDetail
import com.sky.newsapp.newsfeed.NewsBottomBar
import com.sky.newsapp.newsfeed.NewsFeedCard
import com.sky.newsapp.newsfeed.NewsTopbar


var newsFeedObj:DbViewModel?=null
var LoginObj:UserDbViewModel?=null

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookMarkScreen(viewmodel:BookMarkViewmodel= hiltViewModel())
{

    val showDetails= rememberSaveable() {
        mutableStateOf(false)
    }

    val webviewurl= remember {
        mutableStateOf("")
    }

    var bookMarkItems=newsFeedObj!!.SelectBookMark()

    val reload= remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = reload.value)
    {
        bookMarkItems= newsFeedObj!!.SelectBookMark()
    }



Scaffold(topBar = {
    NewsTopbar(title = "Book Marked",showSearch = false)
},
    content = {
    paddingValues ->

    if(bookMarkItems.isEmpty())
    {
        NoBookMark()
    }
    else
    {
        LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.padding(bottom = paddingValues.calculateBottomPadding(),top = paddingValues.calculateTopPadding()))
        {
            items(bookMarkItems)
            {
                articles->
                var isBookamarked by remember {
                    mutableStateOf(newsFeedObj!!.IsBookMarked(articles.name))
                }
                NewsFeedCard(
                    title = articles.title,
                    content = articles.content,
                    url = articles.url,
                    urlToImage = articles.urlToImage,
                    publishedAt = articles.publishedAt,
                    bookmarkClicked = {
                            newsFeedObj!!.DeleteBookMark(articles.name)
                            isBookamarked=false
                            reload.value=!reload.value
                    },
                    isBookamarked = isBookamarked,
                    iconColor = Color.Red,
                    urlClicked = {
                        webviewurl.value = articles.url ?: ""
                        showDetails.value = true
                    }
                )
            }
        }
    }

},
    containerColor = Color.LightGray,
    bottomBar = { NewsBottomBar() }
)

    if(showDetails.value)
    {
        NewsDetail(alertDialo = showDetails, webUrl =webviewurl.value )
    }


    BackHandler {
        if(showDetails.value) {
            showDetails.value = false
        }
        else {
            viewmodel.onBackPress()
        }
    }

}


@Composable
fun NoBookMark()
{
    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally)
    {
        Text("No BookMark Added", fontWeight = FontWeight.Bold, fontSize = 20.sp)
    }
}

@Composable
fun NoDataFound()
{
    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally)
    {
        Text("No Data Found ", fontWeight = FontWeight.Bold, fontSize = 20.sp)
    }
}