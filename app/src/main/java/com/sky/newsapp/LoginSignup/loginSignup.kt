package com.sky.newsapp.LoginSignup

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetState
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sky.Database.UserTable
import com.sky.newsapp.BookMarkScreen.LoginObj
import com.sky.newsapp.R
import com.sky.newsapp.utils.Utils
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun LoginSignup(viewModel: LoginSignupViewModel= hiltViewModel())
{
    val scope = rememberCoroutineScope()

    val bottom_state = rememberBottomSheetScaffoldState(
        bottomSheetState = BottomSheetState(BottomSheetValue.Collapsed)
    )

    BottomSheetScaffold(
        scaffoldState = bottom_state,
        sheetContent = {
            when(viewModel.selected_item.value)
            {
                1-> login(viewModel)
                2 -> signup_page(viewModel)
            }
        },
        sheetShape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp),
        sheetBackgroundColor = Color(0xFF92C1D5),
        sheetPeekHeight =0.dp,
    )
    {

        Column(modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1A81AD))
            .padding(10.dp), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally)
        {
            Text(text = "Welcome to News Reader App!!", textAlign = TextAlign.Center, fontSize =22.sp, fontWeight = FontWeight.ExtraBold,
                color = Color.White)
            Spacer(modifier = Modifier.padding(10.dp))
            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.padding(15.dp))
            {

                LoginSignUpButtons(
                    clickable = {
                        viewModel.selected_item.value = 1
                        scope.launch {
                            if (bottom_state.bottomSheetState.isCollapsed) {
                                bottom_state.bottomSheetState.expand()
                            } else {
                                bottom_state.bottomSheetState.collapse()
                            }

                        }
                    }, title = "Login"
                )


                LoginSignUpButtons(
                    clickable = {
                        viewModel.selected_item.value = 2
                        scope.launch {
                            if (bottom_state.bottomSheetState.isCollapsed) {
                                bottom_state.bottomSheetState.expand()
                            } else {
                                bottom_state.bottomSheetState.collapse()
                            }
                        }
                    }, title = "signup"
                    )



            }

        }

    }

    BackHandler {
        Utils.activity.finishAffinity()
        Utils.activity.finish()
    }
}
@Composable
fun LoginSignUpButtons(clickable:()->Unit,title:String)
{
    Card(shape = RoundedCornerShape(5.dp),
        backgroundColor = Color.White,
        modifier = Modifier
            .padding(20.dp)
            .height(47.dp)
            .width(120.dp)
            .clickable(onClick = {
                clickable.invoke()
            })
    )
    {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(10.dp)
        )
        {

            Text(
                text = title,
                fontSize = 15.sp,
                color = Color.Black, textAlign = TextAlign.Center,
                fontWeight = FontWeight.ExtraBold
            )
        }
    }
}

@SuppressLint("SuspiciousIndentation")
@Composable
fun signup_page(viewModel: LoginSignupViewModel)
{
    val username = remember {
        mutableStateOf("")
    }

    val email = remember {
        mutableStateOf("")
    }

    val password = remember {
        mutableStateOf("")
    }

    val context= LocalContext.current

     val focusManager = LocalFocusManager.current


    Column(modifier = Modifier
        .padding(10.dp)
        .fillMaxHeight(0.9f)
        .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally)
    {

        Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center, modifier = Modifier
            .fillMaxWidth()
            .padding(top = 7.dp))
        {
            Surface(shape = RoundedCornerShape(10.dp), color = Color.Gray, modifier = Modifier
                .width(50.dp)
                .height(5.dp)){}
            Spacer(modifier = Modifier.padding(5.dp))
            Text(text = "SIGNUP", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.Black)
        }

        // user name
        CreditionalTextField(title ="User Name" ,
            textfielvalue = username ,
            placeholderText = "Enter User Name",
            keyboardOptions =KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done),
            keyboardActions =KeyboardActions(onDone = {
                focusManager.clearFocus()
            }
            ), onValueChange = {
                    newtext->
                if(newtext.length<=25) {
                    username.value =
                        newtext.filter { it.isLetterOrDigit() || it.isWhitespace() }
                }
            } )

        Spacer(modifier = Modifier.padding(7.dp))

        // email
        CreditionalTextField(title ="Email",
            textfielvalue = email ,
            placeholderText = "Enter Email",
            keyboardOptions =KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ) ,
            keyboardActions =KeyboardActions(onDone = {
                focusManager.clearFocus()

            }
            ) , onValueChange = {
                email.value=it
            })

        Spacer(modifier = Modifier.padding(7.dp))


        // password
        Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.Start, modifier = Modifier.fillMaxWidth())
        {
            Text(text = "Password", color = Color.Black, fontSize = 14.sp)
            Spacer(modifier = Modifier.padding(4.dp))
            PasswordTextField(text = password.value, onTextChanged = {  password.value = it }, placeholder = {
                Text(
                    text = "Enter Password",
                    color = Color.DarkGray,
                    fontSize = 12.sp
                )

            })
        }

        Spacer(modifier = Modifier.padding(4.dp))


        val isEnable= (username.value.isNotEmpty() &&email.value.isNotEmpty() && password.value.isNotEmpty())


        CreateAccountButton(clickable = {
             if (username.value.trim().isEmpty())
            {
                Toast
                    .makeText(context, "Enter user name", Toast.LENGTH_SHORT)
                    .show()
            }
            else if (!isValidEmail(email.value.trim()))
            {
                Toast
                    .makeText(context, "Enter valid mail id", Toast.LENGTH_SHORT)
                    .show()
            }
            else if (!isValidPassword(password.value.trim()))
            {
                Toast
                    .makeText(context, "Enter valid Password (password should contains at least 8 characters 1 special char, 1 upper case 1 numerical  " , Toast.LENGTH_LONG)
                    .show()
            }
            else {

                 LoginObj!!.InsertUser(UserTable(id = 0, username = username.value, email = email.value, password = password.value))

                 Toast.makeText(context,"Registration Successfully",Toast.LENGTH_SHORT).show()

                    Utils.sharedhelper.putString(context,Utils.username,username.value)
                    Utils.sharedhelper.putString(context,Utils.usermail,email.value)

                 viewModel.onNavigatetoHome()
            }
        },
            cardColor = if(isEnable) Color(0xFF1A81AD) else Color.LightGray,
            textColor = if(isEnable) Color.White else  Color.Black)

        Spacer(modifier = Modifier.padding(5.dp))


     }



}

@Composable
fun CreateAccountButton(clickable: () -> Unit,cardColor:Color,textColor:Color)
{

    Card(backgroundColor =cardColor, modifier = Modifier
        .fillMaxWidth()
        .clickable(onClick = {
            clickable.invoke()
        })
        .padding(10.dp))
    {
        Column(modifier = Modifier
            .padding(7.dp)
            .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center)
        {
            Text(text = "Create Account", color =textColor )
        }
    }
}

@Composable
fun login(viewModel: LoginSignupViewModel)
{

    val focusManager = LocalFocusManager.current


    val email = remember {
        mutableStateOf("")
    }

    val passowrd = remember {
        mutableStateOf("")
    }

    val context= LocalContext.current

    Column(modifier = Modifier
        .padding(10.dp)
        .fillMaxHeight(0.9f),
        verticalArrangement = Arrangement.Top)
    {


        Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center, modifier = Modifier
            .fillMaxWidth()
            .padding(top = 7.dp))
        {
            Surface(shape = RoundedCornerShape(10.dp), color = Color.Gray, modifier = Modifier
                .width(50.dp)
                .height(5.dp)){
            }
            Spacer(modifier = Modifier.padding(5.dp))
            Text(text = "LOGIN", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.Black)
        }

        Spacer(modifier = Modifier.padding(7.dp))

        // email
        CreditionalTextField(title ="Email*" ,
            textfielvalue = email ,
            placeholderText = "Enter Email",
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ) ,
            keyboardActions = KeyboardActions(onDone = {
                focusManager.clearFocus()
            }
            ) , onValueChange = {
                email.value=it
            })

        Spacer(modifier = Modifier.padding(7.dp))


        // password
        Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.Start, modifier = Modifier.fillMaxWidth())
        {
            Text(text = "password*", color = Color.Black, fontSize = 14.sp)
            Spacer(modifier = Modifier.padding(4.dp))
            PasswordTextField(text = passowrd.value, onTextChanged = { passowrd.value = it }, placeholder = {
                Text(
                    text = "Enter Password",
                    color = Color.DarkGray,
                    fontSize = 12.sp
                )
            })
        }
        Spacer(modifier = Modifier.padding(7.dp))

        ContinueButton(title = "Login", cardBgColor =if(email.value.isNotEmpty() && passowrd.value.isNotEmpty()) Color(0xFF1A81AD) else Color.LightGray,
            textColor = if(email.value.isNotEmpty() && passowrd.value.isNotEmpty()) Color.White else Color.Black,
            clickable = {

                if (email.value.trim().isEmpty())
                {
                    Toast
                        .makeText(context, "Enter valid email id", Toast.LENGTH_SHORT)
                        .show()
                }
                else if (passowrd.value.trim().isEmpty())
                {
                    Toast
                        .makeText(context, "Enter valid passowrd", Toast.LENGTH_SHORT)
                        .show()
                }
                else {
                   val userdata= LoginObj!!.GetUser(email = email.value)

                    if(userdata.isEmpty())
                    {
                        Toast.makeText(context,"Record was not found if you are new user register first ",Toast.LENGTH_LONG).show()
                    }
                    else if(userdata[0].password!=passowrd.value)
                    {
                        Toast.makeText(context,"Wrong password",Toast.LENGTH_LONG).show()
                    }
                    else if(userdata[0].password==passowrd.value)
                    {
                        Utils.sharedhelper.putString(context,Utils.username,userdata[0].username)
                        Utils.sharedhelper.putString(context,Utils.usermail,email.value)

                        Toast.makeText(context, "Login Successfully", Toast.LENGTH_SHORT).show()
                        viewModel.onNavigatetoHome()
                    }
                }
            } )
    }

}

@Composable
fun ContinueButton(cardBgColor:Color,textColor:Color,clickable: () -> Unit,title: String)
{
    Card(backgroundColor = cardBgColor, modifier = Modifier
        .fillMaxWidth()
        .clickable(onClick = {
            clickable.invoke()
        })
        .padding(10.dp))
    {
        Column(modifier = Modifier
            .padding(7.dp)
            .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center)
        {
            Text(text = title, color = textColor,
                fontSize = 15.sp)
        }
    }
}

@SuppressLint("ModifierParameter")
@Composable
fun CreditionalTextField(title: String, textfielvalue: MutableState<String>, placeholderText:String,
                         keyboardActions: KeyboardActions = KeyboardActions(),
                         keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
                         modifier:Modifier=Modifier,
                         onValueChange: (String) -> Unit
)
{

    Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.Start, modifier = Modifier.fillMaxWidth())
    {
        Text(text =title , color = Color.Black, fontSize = 14.sp)

        TextField(
            value = textfielvalue.value,
            textStyle = TextStyle(color = Color.Black, fontSize = 17.sp),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.White,
                cursorColor = Color.Black,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            onValueChange = onValueChange,
            modifier = modifier.fillMaxWidth(),
            shape = RoundedCornerShape(5.dp),
            placeholder = {
                Text(text =placeholderText , color = Color.DarkGray, fontSize = 12.sp)
            },
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions)
    }
}


@Composable
fun PasswordTextField(
    text: String,
    modifier: Modifier = Modifier,
    semanticContentDescription: String = "",
    hasError: Boolean = false,
    onTextChanged: (text: String) -> Unit,
    placeholder: @Composable() (() -> Unit)?,
) {
    val focusManager = LocalFocusManager.current
    val showPassword = remember { mutableStateOf(false) }
    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        OutlinedTextField(

            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .semantics { contentDescription = semanticContentDescription },
            value = text,
            onValueChange = onTextChanged,
            placeholder = placeholder,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                }
            ),
            singleLine = true,
            isError = hasError,
            visualTransformation = if (showPassword.value) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val (image, iconColor) = if (showPassword.value) {
                    Pair(
                        R.drawable.baseline_visibility_24, colorResource(R.color.black)
                    )
                } else {
                    Pair(
                        R.drawable.baseline_visibility_off_24, colorResource(R.color.black)
                    )                }

                IconButton(onClick = { showPassword.value = !showPassword.value })
                {
                    Image(painter = painterResource(id = image), contentDescription ="" , colorFilter = ColorFilter.tint(iconColor))

                }
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                backgroundColor = Color.White,
                cursorColor = Color.Black,
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent
            ),
            textStyle = TextStyle(color = Color.Black, fontSize = 17.sp),
            shape = RoundedCornerShape(5.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))

    }
}