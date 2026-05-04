package com.example.crudfirebase.appFirebase.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.crudfirebase.appFirebase.ui.views.HomeScreen
import com.example.crudfirebase.appFirebase.ui.views.LoginUserScreen
import com.example.crudfirebase.appFirebase.ui.views.RegisterUserScreen
import com.example.crudfirebase.appFirebase.ui.views.SplashScreen


/**Nav controller screen**/
@Composable
fun NavigationController() {

    val navController = rememberNavController()

    NavHost(
    navController = navController,
    startDestination = Screen.SplashScreen. route
    ) {
        composable(Screen.SplashScreen.route) {
            SplashScreen(navController)
        }
        composable(Screen.Login.route) {
            LoginUserScreen(navController)
        }
        composable(Screen.Register.route) {
            RegisterUserScreen(navController)
        }
        composable(Screen.HomeScreen.route) {
            HomeScreen(navController)
        }
    }
}