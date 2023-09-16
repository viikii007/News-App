package com.sky.newsapp.profileScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sky.newsapp.BookMarkScreen.LoginObj
import com.sky.newsapp.R
import com.sky.newsapp.utils.Utils

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ProfileScreen(viewModel: ProfileViewModel= hiltViewModel())
{
    
    val context= LocalContext.current
    
    val name=Utils.sharedhelper.getString(context,Utils.username).toString()
    val email=Utils.sharedhelper.getString(context,Utils.usermail).toString()
    
    
    Scaffold(topBar = {
        TopAppBar(title = {
            Box(modifier = Modifier.fillMaxWidth())
            {
                Image(painter = painterResource(id = R.drawable.baseline_arrow_back_24), contentDescription ="", contentScale = ContentScale.FillBounds,
                    modifier = Modifier.align(Alignment.CenterStart).clickable(onClick = {
                        viewModel.onBackPress()
                    }))
                Text(text = "Profile", fontSize = 20.sp,modifier=Modifier.align(Alignment.Center))
            }
        })
    }, content = {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(10.dp), 
            horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center)
        {
            
            Text(text = name, fontSize =18.sp, color = Color.Black)
            Spacer(modifier = Modifier.padding(5.dp))
            Text(text = email, fontSize =18.sp, color = Color.Black)
            Spacer(modifier = Modifier.padding(10.dp))
            Surface(color = Color.Red, shape = RoundedCornerShape(10.dp), modifier = Modifier
                .wrapContentSize()
                .clickable(onClick = {
                    viewModel.onNavigatetoLogin()
                     Utils.sharedhelper.putString(context,Utils.username,"")
                    Utils.sharedhelper.putString(context,Utils.usermail,"")
                }))
            {
                Text(text = "Logout", fontSize = 15.sp, color = Color.White, modifier = Modifier.padding(10.dp))
            }
        }
    })
}