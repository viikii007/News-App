package com.sky.newsapp.detailScreen

import android.annotation.SuppressLint
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.sky.newsapp.R

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun NewsDetail(alertDialo: MutableState<Boolean>, webUrl:String) {
    val progressstate = remember { mutableStateOf(0) }
    val context= LocalContext.current
    val network_status = remember { mutableStateOf(checkForInternet(context)) }

    AnimatedVisibility(visible = alertDialo.value)
    {
        Box(modifier = Modifier
            .fillMaxSize()
            .background(Color.White))
        {
            if(!network_status.value)
            {
                Nointernetconnection()
            }
            else {
                Column(modifier = Modifier.fillMaxSize())
                {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(7.dp),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.Top
                    )
                    {
                        Image(
                            painter = painterResource(id = R.drawable.cancel),
                            contentDescription = "Cancel",
                            modifier = Modifier.clickable(onClick = { alertDialo.value = false }),
                            colorFilter = ColorFilter.tint(Color.Black)
                        )
                    }
                    AndroidView(factory = {
                        WebView(it).apply {
                            layoutParams = ViewGroup.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT
                            )
                            val webSettings = this.settings
                            webSettings.javaScriptEnabled = true
                            this.webViewClient = WebViewClient()
                            WebView.setWebContentsDebuggingEnabled(false)
                            this.webChromeClient = object : WebChromeClient() {

                                override fun onProgressChanged(
                                    view: WebView, newProgress: Int
                                ) {
                                    progressstate.value = newProgress
                                }
                            }


                            loadUrl(webUrl)
                        }
                    }, update = {
                    }, modifier = Modifier.fillMaxSize())

                }
                if (progressstate.value != 100) {
                    customProgressBar()
                }

            }
        }
    }
}

@Composable
fun customProgressBar()
{
    Column(modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator(color = Color.Blue)
    }
}

@Composable
fun Nointernetconnection()
{
    Column(modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "No Internet Connection", fontSize = 20.sp, fontWeight = FontWeight.Bold)
    }
}