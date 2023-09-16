package com.sky.newsapp.searchScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sky.newsapp.R
import com.sky.newsapp.utils.Utils

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(viewmodel:SearchViewModel= hiltViewModel())
{
    var text by remember {
        mutableStateOf("")
    }

    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp), verticalAlignment = Alignment.Top)
    {
        TextField(value = text, onValueChange = {text=it}, trailingIcon = {
            Image(painter = painterResource(id = R.drawable.baseline_search_24), contentDescription ="", modifier =
            Modifier.clickable (onClick = {
                if(text.isEmpty())
                {
                    viewmodel.onBackPress()
                }
                else
                {
                    Utils.search=text
                    viewmodel.onBackPress()
                }
            }))
        }, shape = RoundedCornerShape(5.dp), placeholder = {
            Text("Search Here ")
        }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = {
            if(text.isEmpty())
            {
                viewmodel.onBackPress()
            }
            else
            {
                Utils.search=text
                viewmodel.onBackPress()
            }
        }))
    }

}