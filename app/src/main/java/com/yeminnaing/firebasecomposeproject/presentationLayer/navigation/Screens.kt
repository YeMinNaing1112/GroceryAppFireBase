package com.yeminnaing.firebasecomposeproject.presentationLayer.navigation

import kotlinx.serialization.Serializable

//enum class Screens {
//    LogInScreen,
//    SignInScreen,
//    GroceryScreen,
//}
//
//
//sealed class GroceryAppScreens(val route:String){
//data object LogInScreen:GroceryAppScreens(route = Screens.LogInScreen.name)
//data object SignInScreen:GroceryAppScreens(route = Screens.SignInScreen.name)
//data object GroceryScreen:GroceryAppScreens(route = Screens.GroceryScreen.name)
//}
@Serializable
sealed class GroceryAppScreens {
    @Serializable
    data object LogInScreen : GroceryAppScreens()

    @Serializable
    data object SignInScreen : GroceryAppScreens()

    @Serializable
    data object GroceryScreen : GroceryAppScreens()
}