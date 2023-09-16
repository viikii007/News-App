package com.sky.newsapp.newsfeed

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter.Companion.tint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.sky.Database.NewsTable
import com.sky.newsapp.Apiservice.DataOrException
import com.sky.newsapp.Apiservice.NewsViewModel
import com.sky.newsapp.BookMarkScreen.NoDataFound
import com.sky.newsapp.BookMarkScreen.newsFeedObj
import com.sky.newsapp.R
import com.sky.newsapp.detailScreen.NewsDetail
import com.sky.newsapp.detailScreen.Nointernetconnection
import com.sky.newsapp.detailScreen.checkForInternet
import com.sky.newsapp.detailScreen.customProgressBar
import com.sky.newsapp.utils.Utils
import com.sky.newsapp.utils.Utils.Companion.search

var PeofileUpdateViewModelObj :NewsViewModel?=null


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsHome()
{
    val profileData = produceState(initialValue = DataOrException(loading = true))
    {
        value = PeofileUpdateViewModelObj!!.GetNews(q = search, from = "2023-08-15", sortby = "publishedAt", apiKey = Utils.apikey)!!
    }.value

    val context= LocalContext.current
    val network_status = remember { mutableStateOf(checkForInternet(context)) }

    val showDetails= rememberSaveable() {
        mutableStateOf(false)
    }

    val webviewurl= remember {
        mutableStateOf("")
    }

    Scaffold(
        topBar = {
            NewsTopbar(title = "NEWS APP",showSearch = true)
        },
        content = {
                padding->
            if(!network_status.value)
            {
                Nointernetconnection()
            }
            else
            {
                if (profileData.loading == true)
                {
                    customProgressBar()
                }
                else if (profileData.data != null)
                {
                    if (profileData.data?.status == "ok" && profileData.data?.articles!!.isNotEmpty())
                    {
                        val articles = profileData.data!!.articles

                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(10.dp),
                            modifier = Modifier.padding(
                                bottom = padding.calculateBottomPadding(),
                                top = padding.calculateTopPadding()
                            )
                        )
                        {
                            items(articles)
                            { articles ->

                                var isBookamarked by remember {
                                    mutableStateOf(newsFeedObj!!.IsBookMarked(articles.source.name))
                                }


                                NewsFeedCard(
                                    articles.title ?: "",
                                    articles.content ?: "",
                                    articles.url ?: "",
                                    articles.urlToImage ?: "",
                                    articles.publishedAt ?: "",
                                    bookmarkClicked = {
                                        if (isBookamarked) {
                                            newsFeedObj!!.DeleteBookMark(articles.source.name)
                                            isBookamarked = false
                                        } else {
                                            newsFeedObj!!.InsertNewsFeed(
                                                NewsTable(
                                                    id = 0,
                                                    content = articles.content,
                                                    description = articles.description,
                                                    publishedAt = articles.publishedAt,
                                                    title = articles.title,
                                                    url = articles.url,
                                                    urlToImage = articles.urlToImage,
                                                    name = articles.source.name
                                                )
                                            )
                                            isBookamarked = true
                                        }
                                    },
                                    iconColor = if (isBookamarked) Color.Red else Color.LightGray
                                ) {
                                    webviewurl.value = articles.url ?: ""
                                    showDetails.value = true
                                }
                            }
                        }
                    }
                    else
                    {
                        NoDataFound()
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
            Utils.activity.finishAffinity()
            Utils.activity.finish()
        }
    }




}



@Composable
fun NewsFeedCard(
    title: String,
    content: String,
    url: String,
    urlToImage: String,
    publishedAt: String,
    bookmarkClicked: () -> Unit,
    iconColor: Color = Color.Black,
    urlClicked: () -> Unit,
) {

    Card(
        modifier = Modifier.padding(10.dp),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    )
    {
        Column(modifier = Modifier.fillMaxWidth())
        {
            NewsFeedImage(urlToImage)

            Row(
                modifier = Modifier.fillMaxWidth().padding(5.dp),
                verticalAlignment = Alignment.CenterVertically
            )
            {
                Text(
                    text = title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Left,
                    modifier = Modifier
                        .weight(4f)
                        .padding(4.dp)
                )

                Box(
                    modifier = Modifier
                        .weight(0.4f)
                        .clickable(onClick = {
                            bookmarkClicked()
                        })
                        .size(25.dp)
                )
                {
                    Image(
                        painter = painterResource(id = R.drawable.bookmark),
                        contentDescription = "",
                        contentScale = ContentScale.FillBounds, colorFilter = tint(
                            iconColor
                        )
                    )

                }

            }


            Text(
                text = publishedAt,
                fontSize = 10.sp,
                textAlign = TextAlign.Left,
                modifier = Modifier.padding(7.dp)
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = content,
                textAlign = TextAlign.Justify,
                color = Color.Black,
                fontSize = 14.sp,
                modifier = Modifier.padding(10.dp),
                lineHeight = 15.sp
            )
            Text(text = url,
                color = Color.Blue,
                fontSize = 13.sp,
                lineHeight = 15.sp,
                modifier = Modifier
                    .padding(10.dp)
                    .clickable(onClick = {
                        urlClicked()
                    })
            )
        }
    }


}




@Composable
fun NewsFeedImage(urlToImage:String)
{
    SubcomposeAsyncImage(
        model =  urlToImage,
        contentDescription = "", contentScale = ContentScale.FillBounds
    ) {
        val state = painter.state
        if (state is AsyncImagePainter.State.Loading || state is AsyncImagePainter.State.Error) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator(modifier = Modifier.padding(7.dp))
            }
        } else {
            SubcomposeAsyncImageContent(contentScale = ContentScale.FillBounds)

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsTopbar(title: String,showSearch:Boolean,viewModel: NewsFeedViewModel= hiltViewModel())
{
    val context= LocalContext.current
    val name=Utils.sharedhelper.getString(context,Utils.username).toString()

    TopAppBar(title = {
        Box(modifier = Modifier.fillMaxWidth())
        {

            if(showSearch) {
                Row(
                    modifier = Modifier.align(Alignment.CenterStart),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                )
                {
                    Image(
                        painter = painterResource(id = R.drawable.profile),
                        contentDescription = "commonProfile",
                        modifier = Modifier
                            .size(40.dp)
                            .clickable(onClick = {
                                viewModel.onNavigatetoProfile()
                            }),
                    )
                    Text(
                        text = "Hi $name",
                        fontSize = 15.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.W700,
                        modifier = Modifier
                    )

                }
            }

            Text(text = title, fontSize = 18.sp, fontWeight = FontWeight.ExtraBold, textAlign = TextAlign.Center,
                modifier = Modifier.align(Alignment.Center))

            if(showSearch)
            {
                Box(  modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .size(25.dp)
                    .clickable(onClick = {
                        viewModel.onNavigatetoSearchScreen()
                    }))
                {
                    Image(
                        painter = painterResource(id = R.drawable.baseline_search_24),
                        contentDescription = "search",
                        contentScale = ContentScale.FillBounds,
                    )
                }
            }

        }
    },colors = TopAppBarDefaults.mediumTopAppBarColors(Color.LightGray))
}


@Composable
fun NewsBottomBar(viwmodel: NewsFeedViewModel= hiltViewModel())
{
    BottomAppBar(containerColor = Color.White)
    {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp), horizontalArrangement = Arrangement.SpaceEvenly)
        {
            Box(modifier = Modifier
                .size(40.dp)
                .background(
                    if (Utils.currentScreen == "home") Color.Blue else Color.Transparent,
                    shape = CircleShape
                )
                .clip(
                    CircleShape
                ))
            {
                Image(
                    painter = painterResource(id = R.drawable.home),
                    contentDescription = "home",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .size(30.dp)
                        .align(Alignment.Center)
                        .clickable(onClick = {
                            if (Utils.currentScreen != "home")
                                viwmodel.onNavigatetoNewsFeed()
                        }),
                    colorFilter = tint(if(Utils.currentScreen=="home") Color.White else Color.Black)
                )
            }

            Box(modifier = Modifier
                .size(40.dp)
                .background(
                    if (Utils.currentScreen == "bookmark") Color.Blue else Color.Transparent,
                    shape = CircleShape
                ))
            {
                Image(
                    painter = painterResource(id = R.drawable.bookmark),
                    contentDescription = "bookmark",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .size(30.dp)
                        .align(Alignment.Center)
                        .clickable(onClick = {
                            viwmodel.onNavigatetoFavorite()
                        }),
                    colorFilter = tint(if(Utils.currentScreen=="bookmark") Color.White else Color.Black)
                )
            }
        }
    }
}