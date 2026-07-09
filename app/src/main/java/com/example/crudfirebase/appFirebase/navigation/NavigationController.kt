package com.example.crudfirebase.appFirebase.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.crudfirebase.appFirebase.ui.viewModelShopping.CartViewModel
import com.example.crudfirebase.appFirebase.ui.views.AdminOrdersScreen
import com.example.crudfirebase.appFirebase.ui.views.EditProfileUserScreen
import com.example.crudfirebase.appFirebase.ui.views.HomeScreen
import com.example.crudfirebase.appFirebase.ui.views.ListUserScreen
import com.example.crudfirebase.appFirebase.ui.views.LoginUserScreen
import com.example.crudfirebase.appFirebase.ui.views.ProfileUserScreenInfo
import com.example.crudfirebase.appFirebase.ui.views.RegisterUserScreen
import com.example.crudfirebase.appFirebase.ui.views.ShoppingScreen
import com.example.crudfirebase.appFirebase.ui.views.SplashScreen
import com.example.crudfirebase.appFirebase.ui.views.product.AddProductScreen
import com.example.crudfirebase.appFirebase.ui.views.product.ProductListAdmin


/**Nav controller screen**/
@Composable
fun NavigationController() {

    val navController = rememberNavController()
    val cartViewModel: CartViewModel = viewModel()

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
            HomeScreen(
                navController = navController,
                cartViewModel = cartViewModel
            )
        }
        composable(Screen.EditProfileUserScreen.route) {
            EditProfileUserScreen(navController)
        }
        composable(Screen.ProfileUserScreenInfo.route) {
            ProfileUserScreenInfo(navController)
        }
        composable(Screen.ListUserScreen.route) {
            ListUserScreen(navController)
        }
        composable(Screen.ProductListAdmin.route) {
            ProductListAdmin(navController)
        }
        composable(Screen.AddProductScreen.route) {
            AddProductScreen(navController)
        }
        composable(Screen.ShoppingScreen.route+ "/{name}/{phone}/{email}") {
            ShoppingScreen(
                navController = navController,
                cartViewModel = cartViewModel,

                it.arguments?.getString("name"),
                it.arguments?.getString("phone"),
                it.arguments?.getString("email"),
            )
        }
        composable(Screen.AdminOrdersScreen.route) {
            AdminOrdersScreen(navController = navController)
        }



    }
}