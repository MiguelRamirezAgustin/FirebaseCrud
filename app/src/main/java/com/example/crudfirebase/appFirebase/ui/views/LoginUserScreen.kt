package com.example.crudfirebase.appFirebase.ui.views

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.crudfirebase.R
import com.example.crudfirebase.appFirebase.data.remote.FirebaseAuthService
import com.example.crudfirebase.appFirebase.data.repository.AuthRepository
import com.example.crudfirebase.appFirebase.navigation.Screen
import com.example.crudfirebase.appFirebase.ui.components.EmailInputField
import com.example.crudfirebase.appFirebase.ui.components.PasswordInputField
import com.example.crudfirebase.appFirebase.ui.components.SlideToConfirmButton
import com.example.crudfirebase.appFirebase.viewmodel.AuthViewModel
import com.example.crudfirebase.ui.theme.color_blue_backgraund
import com.example.crudfirebase.ui.theme.color_write

@Composable
fun LoginUserScreen(navController: NavHostController) {
    val viewModel: AuthViewModel = hiltViewModel()

    var email = remember { mutableStateOf("") }
    var password = remember { mutableStateOf("") }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {

            },
            bottomBar = {

            }

        ) { innerPadding ->

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color_blue_backgraund)
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 40.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape)
                            .background(Color.White),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.iconfirebase),
                            contentDescription = "firebase icon",
                            modifier = Modifier.size(65.dp),
                            tint = Color.Unspecified
                        )
                    }
                    Spacer(Modifier.height(15.dp))

                    Text(
                        text = "Login",
                        color = Color.Black,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(Modifier.height(24.dp))
                    EmailInputField(
                        value = email.value,
                        onValueChange = { email.value = it },
                        placeholder = "",
                        isFocused = false,
                        isError = false
                    )

                    Spacer(Modifier.height(16.dp))
                    PasswordInputField(
                        value = password.value,
                        onValueChange = { password.value = it },
                        placeholder = "",
                    )


                    Spacer(Modifier.height(35.dp))
                    SlideToConfirmButton(text = "Aceptar", onComplete = {
                        if (email.value.isNotEmpty() && password.value.isNotEmpty()) {
                            viewModel.login(email.value, password.value)
                        } else {
                            Log.e("LOGIN", "Campos vacíos")
                        }
                    })
                    Spacer(Modifier.height(35.dp))
                    Text(
                        "¿Aún no te gas registrado?", color = color_write, fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.clickable {
                            navController.navigate(Screen.Register.route)
                        }
                    )


                }

            }
        }
    }


}