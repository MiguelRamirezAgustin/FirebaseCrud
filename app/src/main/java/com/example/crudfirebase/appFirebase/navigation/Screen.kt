package com.example.crudfirebase.appFirebase.navigation

sealed class Screen(val route:String) {
    object Login : Screen("LoginUserScreen")
    object Register : Screen("RegisterUserScreen")
    object HomeScreen : Screen("HomeScreen")
    object SplashScreen : Screen("SplashScreen")
    object EditProfileUserScreen : Screen("EditProfileUserScreen")
    object ProfileUserScreenInfo : Screen("ProfileUserScreenInfo")
}