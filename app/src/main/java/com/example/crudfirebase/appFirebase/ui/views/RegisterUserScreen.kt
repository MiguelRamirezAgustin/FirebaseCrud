package com.example.crudfirebase.appFirebase.ui.views


import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.crudfirebase.appFirebase.data.remote.FirebaseAuthService
import com.example.crudfirebase.appFirebase.data.repository.AuthRepository
import com.example.crudfirebase.appFirebase.ui.components.EmailInputField
import com.example.crudfirebase.appFirebase.ui.components.PasswordInputField
import com.example.crudfirebase.appFirebase.ui.components.SlideToConfirmButton
import com.example.crudfirebase.appFirebase.viewmodel.AuthViewModel
import com.example.crudfirebase.ui.theme.color_write

@Composable
fun RegisterUserScreen(navController: NavHostController) {
    val viewModel: AuthViewModel = hiltViewModel()

    val user = viewModel.user.value
    val isLoading = viewModel.isLoading.value
    val error = viewModel.error.value
    var emailRegisterUser = remember { mutableStateOf("") }
    var passwordRegisterUser = remember { mutableStateOf("") }


    LaunchedEffect(user) {
        if (user != null) {
            Log.d("", " Register user")
            //Navegacion a pantalla de inicio
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        if (isLoading) {
            LoadingScreen()
        }
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
                    .background(color_write)
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

                    Text(
                        text = "Registro",
                        color = Color.Black,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(Modifier.height(24.dp))
                    EmailInputField(
                        value = emailRegisterUser.value,
                        onValueChange = { emailRegisterUser.value = it },
                        placeholder = "Correo electronico",
                        isFocused = false,
                        isError = false
                    )

                    Spacer(Modifier.height(16.dp))
                    PasswordInputField(
                        value = passwordRegisterUser.value,
                        onValueChange = { passwordRegisterUser.value = it },
                        placeholder = "Contraseña",
                    )


                    Spacer(Modifier.height(35.dp))
                    SlideToConfirmButton(text = "Aceptar", enabled = !isLoading, onComplete = {
                        if (emailRegisterUser.value.isNotEmpty() && passwordRegisterUser.value.isNotEmpty()) {
                            viewModel.registerUser(
                                emailRegisterUser.value,
                                passwordRegisterUser.value
                            )
                        } else {
                            Log.e("LOGIN", "Campos vacíos")
                        }
                    })
                    Spacer(Modifier.height(35.dp))
                    error?.let {
                        Text(
                            text = it,
                            color = Color.Red,
                            fontSize = 14.sp
                        )
                    }
                }
            }
            if (isLoading) {
                LoadingScreen()
            }
        }
    }
}