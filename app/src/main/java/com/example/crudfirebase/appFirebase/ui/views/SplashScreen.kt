package com.example.crudfirebase.appFirebase.ui.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.crudfirebase.R
import com.example.crudfirebase.appFirebase.navigation.Screen
import com.example.crudfirebase.ui.theme.color_blue
import com.example.crudfirebase.ui.theme.color_write
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {

    LaunchedEffect(Unit) {

        delay(2000)

        val currentUser = FirebaseAuth
            .getInstance()
            .currentUser

        if (currentUser != null) {

            navController.navigate(Screen.HomeScreen.route ) {
                popUpTo(Screen.HomeScreen.route) {
                    inclusive = true
                }
            }

        } else {
            navController.navigate(Screen.Login.route) {
                popUpTo(Screen.Login.route) {
                    inclusive = true
                }
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize().background(color_blue),
        contentAlignment = Alignment.Center
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Box(
                modifier = Modifier
                    .size(80.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(R.drawable.iconfirebase),
                    contentDescription = "firebase icon",
                    modifier = Modifier.size(120.dp),
                    tint = Color.Unspecified
                )
            }
            Spacer(modifier = Modifier.height(50.dp))
            CircularProgressIndicator(  color = color_write)
        }
    }
}