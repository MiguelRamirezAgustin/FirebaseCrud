package com.example.crudfirebase.appFirebase.ui.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.crudfirebase.appFirebase.navigation.Screen
import com.google.firebase.auth.FirebaseAuth


@Composable
fun HomeScreen(navController: NavController){
    Column(modifier = Modifier.fillMaxSize()) {
        Spacer(modifier = Modifier.height(200.dp))
        Button(
            onClick = {

                FirebaseAuth.getInstance().signOut()

                navController.navigate(Screen.Login.route) {

                    popUpTo(Screen.HomeScreen.route) {
                        inclusive = true
                    }
                }
            }
        ) {
            Text("Cerrar sesión")
        }
    }

}