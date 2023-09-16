package com.sky.newsapp.splashScreen

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.sky.newsapp.BookMarkScreen.LoginObj
import com.sky.newsapp.R
import com.sky.newsapp.utils.Utils
import kotlinx.coroutines.delay

@SuppressLint("SuspiciousIndentation")
@Composable
fun AnimatedSplashScreen(viewmodel: SplashViewModel = hiltViewModel())
{
    val context= LocalContext.current

    val isLoggedin=Utils.sharedhelper.getString(context,Utils.usermail).toString()

     var startAnimation by remember { mutableStateOf(false) }
    val alphaAnim = animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(durationMillis = 3000), label = ""
    )

    LaunchedEffect(key1 = true)
    {
        startAnimation = true
        delay(2000)

        if(isLoggedin.isEmpty())
        {
            viewmodel.onNavigatetoLogin()
        }
        else {
            viewmodel.onNavigatetoHomeScreen()

        }

    }

    Splash(alpha = alphaAnim.value)


}

@Composable
fun Splash(alpha: Float)
{
    Box(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = "splashIcon",
            modifier = Modifier.alpha(alpha = alpha),
            colorFilter = ColorFilter.tint(Color.Green)
        )
    }
}