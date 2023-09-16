package com.sky.navigation

import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.sky.newsapp.BookMarkScreen.BookMarkScreen
import com.sky.newsapp.LoginSignup.LoginSignup
import com.sky.newsapp.MainViewModel
import com.sky.newsapp.newsfeed.NewsHome
import com.sky.newsapp.profileScreen.ProfileScreen
import com.sky.newsapp.searchScreen.SearchScreen
import com.sky.newsapp.splashScreen.AnimatedSplashScreen
import com.sky.newsapp.utils.Utils
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

@SuppressLint("SuspiciousIndentation")
@Composable
fun SetNavigation(mainViewModel: MainViewModel = hiltViewModel())
{

    val navController = rememberNavController()

    NavigationEffects(navigationChannel = mainViewModel.navigationChannel,
        navHostController = navController
    )



    NavHost(navController = navController,startDestination = DestinationClass.SplashScreen)
    {
        composable(destination= DestinationClass.SplashScreen)
        {
            AnimatedSplashScreen()
        }


        composable(destination =DestinationClass.LoginSignup )
        {
            LoginSignup()
        }

        composable(destination = DestinationClass.NewsFeed)
        {
            Utils.currentScreen="home"
            NewsHome()
        }

        composable(destination = DestinationClass.BookMarkScreen)
        {
            Utils.currentScreen="bookmark"
            BookMarkScreen()
        }

        composable(destination = DestinationClass.SearchScreen)
        {
            SearchScreen()
        }

        composable(destination = DestinationClass.Profie)
        {
            ProfileScreen()
        }



    }

}


@Composable
fun NavigationEffects(
    navigationChannel: Channel<NavigationIntent>,
    navHostController: NavHostController
) {
    val activity = (LocalContext.current as? Activity)
    LaunchedEffect(activity, navHostController, navigationChannel)
    {
        navigationChannel.receiveAsFlow().collect { intent ->
            if (activity?.isFinishing == true) {
                return@collect
            }
            when (intent)
            {
                is NavigationIntent.NavigateBack -> {
                    if (intent.route != null) {
                        navHostController.popBackStack(intent.route, intent.inclusive)
                    } else {
                        navHostController.popBackStack()
                    }
                }
                is NavigationIntent.NavigateTo -> {
                    navHostController.navigate(intent.route) {
                        launchSingleTop = intent.isSingleTop
                        intent.popUpToRoute?.let { popUpToRoute ->
                            popUpTo(popUpToRoute) { inclusive = intent.inclusive }
                        }
                    }
                }

            }
        }
    }
}
















