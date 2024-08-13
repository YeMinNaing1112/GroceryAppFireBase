package com.yeminnaing.firebasecomposeproject.presentationLayer.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.yeminnaing.firebasecomposeproject.presentationLayer.GroceryScreen
import com.yeminnaing.firebasecomposeproject.presentationLayer.authScreen.LogInScreen
import com.yeminnaing.firebasecomposeproject.presentationLayer.authScreen.SignInScreen

@Composable
fun GroceryScreenNavGraph() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = GroceryAppScreens.SignInScreen.route
    ) {
          composable(
           route=GroceryAppScreens.LogInScreen.route
          ){
            LogInScreen(navController)
          }
        composable(
            route=GroceryAppScreens.SignInScreen.route
        ){
            SignInScreen(navController)
        }
        composable(
            route=GroceryAppScreens.GroceryScreen.route
        ){
            GroceryScreen(navController = navController)
        }
    }
}