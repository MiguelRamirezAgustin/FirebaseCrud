package com.example.crudfirebase.appFirebase.navigation

sealed class Screen(val route:String) {
    object Login : Screen("LoginUserScreen")
    object Register : Screen("RegisterUserScreen")
    object HomeScreen : Screen("HomeScreen")
    object SplashScreen : Screen("SplashScreen")
    object EditProfileUserScreen : Screen("EditProfileUserScreen")
    object ProfileUserScreeInfo : Screen("ProfileUserScreeInfo")
    object ListUserScreen : Screen("ListUserScreen")
    object ProductListAdmin : Screen("ProductListAdmin")
    object AddProductScreen : Screen("AddProductScreen")
    object ShoppingScreen : Screen("ShoppingScreen")
    object AdminOrdersScreen : Screen("AdminOrdersScreen")
    object OrderListClienScren : Screen("OrderListClienScren")


}