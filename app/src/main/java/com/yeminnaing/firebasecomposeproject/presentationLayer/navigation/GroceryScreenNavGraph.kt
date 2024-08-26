package com.yeminnaing.firebasecomposeproject.presentationLayer.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.yeminnaing.firebasecomposeproject.presentationLayer.GroceryScreen
import com.yeminnaing.firebasecomposeproject.presentationLayer.authScreen.LogInScreen
import com.yeminnaing.firebasecomposeproject.presentationLayer.authScreen.SignInScreen

//region Navigation With route
//@Composable
//fun GroceryScreenNavGraph() {
//    val navController = rememberNavController()
//    NavHost(
//        navController = navController,
//        startDestination = GroceryAppScreens.SignInScreen.route
//    ) {
//          composable(
//           route=GroceryAppScreens.LogInScreen.route
//          ){
//            LogInScreen(navController)
//          }
//        composable(
//            route=GroceryAppScreens.SignInScreen.route
//        ){
//            SignInScreen(navController)
//        }
//        composable(
//            route=GroceryAppScreens.GroceryScreen.route
//        ){
//            GroceryScreen(navController = navController)
//        }
//    }
//}
//endregion

@Composable
fun GroceryScreenNavGraph() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = GroceryAppScreens.SignInScreen
    ) {
        composable<GroceryAppScreens.LogInScreen> {
            LogInScreen(navController)
        }
        composable<GroceryAppScreens.SignInScreen> {
            SignInScreen(navController)
        }
        composable<GroceryAppScreens.GroceryScreen>{
            GroceryScreen(navController = navController)
        }
    }
}