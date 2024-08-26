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
import com.yeminnaing.firebasecomposeproject.dataLayer.analytic.FireBaseAnalyticManager
import com.yeminnaing.firebasecomposeproject.dataLayer.analytic.PARAMETER_EMAIL
import com.yeminnaing.firebasecomposeproject.dataLayer.analytic.SCREEN_LOGIN
import com.yeminnaing.firebasecomposeproject.dataLayer.analytic.SCREEN_SIGNIN
import com.yeminnaing.firebasecomposeproject.dataLayer.analytic.TAP_SIGNIN
import com.yeminnaing.firebasecomposeproject.presentationLayer.navigation.GroceryAppScreens

@Composable
fun SignInScreen(navController: NavHostController) {
    val viewModel: AuthenticationVm = hiltViewModel()
    val context= LocalContext.current
    val authState by viewModel.authState.collectAsState()
    val fireBaseAnalyticManager= FireBaseAnalyticManager()

    LaunchedEffect(Unit){
        fireBaseAnalyticManager.sendEventsToFireBaseAnalytic(context, SCREEN_SIGNIN)
    }
    LaunchedEffect(authState) {
        when (authState) {
            is AuthState.Authenticated -> navController.navigate(GroceryAppScreens.GroceryScreen) {
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

    SignInScreenDesign(modifier=Modifier,
        signIn = {  name, email, password ->
                 fireBaseAnalyticManager.sendEventsToFireBaseAnalytic(context, TAP_SIGNIN,
                     PARAMETER_EMAIL,email)
                 viewModel.signIn(name = name,email=email, password=password)
        },
        navigation = {
            navController.navigate(GroceryAppScreens.LogInScreen){
                popUpTo(navController.graph.findStartDestination().id){
                    saveState=true
                    inclusive=false
                }
              launchSingleTop=true
            }
        })

}

@Composable
fun SignInScreenDesign(modifier: Modifier = Modifier,signIn:(name:String,email:String,password:String)->Unit ,navigation:()->Unit) {
    val keyboardController = LocalSoftwareKeyboardController.current

    val emailFocusRequester = remember {
        FocusRequester()
    }
    val passwordRequester = remember {
        FocusRequester()
    }
    val nameRequester = remember {
        FocusRequester()
    }


    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    var name by remember {
        mutableStateOf("")
    }

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            value = name,
            onValueChange = { name = it },
            placeholder = { Text(text = "Name") },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = {
                    emailFocusRequester.requestFocus()
                }
            ),
            modifier = modifier.focusRequester(nameRequester)
        )

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
               signIn(name,email,password)
        }) {
            Text(text = "SignIn")
        }

        Text(text = "If you already have an account ")
        TextButton(onClick = {
           navigation()
        }) {
            Text(text = "LoginHere")
        }
    }
}

@Preview
@Composable
fun SignInScreenDesignPrev() {
    SignInScreenDesign(signIn = {name, email, password ->  },

        navigation = {})
}