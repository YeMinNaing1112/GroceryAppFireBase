package com.yeminnaing.firebasecomposeproject.presentationLayer.authScreen

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.yeminnaing.firebasecomposeproject.presentationLayer.navigation.GroceryAppScreens

@Composable
fun LogInScreen(navController: NavHostController) {

    val viewModel: AuthenticationVm = hiltViewModel()
    val context= LocalContext.current
    val authState by viewModel.authState.collectAsState()
    LaunchedEffect(authState) {
        when (authState) {
            is AuthState.Authenticated -> navController.navigate(GroceryAppScreens.GroceryScreen.route) {
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = false
                    inclusive = true
                }
                launchSingleTop = true
            }

            is AuthState.Empty -> {}
            is AuthState.Error -> {
                Toast.makeText(context,((authState as AuthState.Error).message) , Toast.LENGTH_SHORT).show()
            }
            AuthState.Loading -> {
                Toast.makeText(context,"Loading" , Toast.LENGTH_SHORT).show()
            }
            else -> {}
        }
    }
   LogInScreenDesign(logIn = {email, password ->
                 viewModel.logIn(email, password)
   }
       , navigation = {
           navController.navigate(GroceryAppScreens.SignInScreen.route){
               popUpTo(navController.graph.findStartDestination().id){
                   saveState=true
                   inclusive=false
               }
               launchSingleTop=true
           }
       })
}

@Composable
fun LogInScreenDesign(modifier: Modifier = Modifier,logIn:(email:String,password:String)->Unit ,navigation:()->Unit) {
    val keyboardController = LocalSoftwareKeyboardController.current

    val emailFocusRequester = remember {
        FocusRequester()
    }
    val passwordRequester = remember {
        FocusRequester()
    }


    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            value = email,
            onValueChange = { email = it },
            placeholder = { Text(text = "Email") },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = {
                    passwordRequester.requestFocus()
                }
            ),
            modifier = modifier.focusRequester(emailFocusRequester)
        )


        TextField(
            value = password,
            onValueChange = { password = it },
            placeholder = { Text(text = "Password") },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    keyboardController?.hide()
                }
            )
        )

        Button(onClick = {
            logIn(email, password)
        }) {
            Text(text = "Login")
        }

        Text(text = "If you don't have any account ")
        TextButton(onClick = {
            navigation()
        }) {
            Text(text = "SignIn")
        }
    }
}

@Preview
@Composable
fun LogInScreenDesignPrev() {
    LogInScreenDesign(logIn = {email, password ->  },
        navigation = {})
}