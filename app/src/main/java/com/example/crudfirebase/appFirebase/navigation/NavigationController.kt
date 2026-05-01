package com.example.crudfirebase.appFirebase.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.crudfirebase.appFirebase.ui.views.LoginUserScreen
import com.example.crudfirebase.appFirebase.ui.views.RegisterUserScreen


@Composable
fun NavigationController() {

    val navController = rememberNavController()

    NavHost(
    navController = navController,
    startDestination = Screen.Login.route
    ) {

        composable(Screen.Login.route) {
            LoginUserScreen(navController)
        }

        composable(Screen.Register.route) {
            RegisterUserScreen(navController)
        }


    }
}