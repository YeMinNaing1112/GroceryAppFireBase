package com.yeminnaing.firebasecomposeproject.presentationLayer.navigation

enum class Screens {
    LogInScreen,
    SignInScreen,
    GroceryScreen,
}


sealed class GroceryAppScreens(val route:String){
data object LogInScreen:GroceryAppScreens(route = Screens.LogInScreen.name)
data object SignInScreen:GroceryAppScreens(route = Screens.SignInScreen.name)
data object GroceryScreen:GroceryAppScreens(route = Screens.GroceryScreen.name)
}