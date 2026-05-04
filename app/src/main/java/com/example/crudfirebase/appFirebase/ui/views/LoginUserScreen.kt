package com.example.crudfirebase.appFirebase.ui.views

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.crudfirebase.R
import com.example.crudfirebase.appFirebase.navigation.Screen
import com.example.crudfirebase.appFirebase.ui.components.CustomAlertDialog
import com.example.crudfirebase.appFirebase.ui.components.EmailInputField
import com.example.crudfirebase.appFirebase.ui.components.PasswordInputField
import com.example.crudfirebase.appFirebase.ui.components.SlideToConfirmButton
import com.example.crudfirebase.appFirebase.viewmodel.AuthViewModel
import com.example.crudfirebase.appFirebase.viewmodel.UiState
import com.example.crudfirebase.ui.theme.color_blue_backgraund
import com.example.crudfirebase.ui.theme.color_write

@Composable
fun LoginUserScreen(navController: NavHostController) {
    val viewModel: AuthViewModel = hiltViewModel()

    val state = viewModel.state.value
    var email = remember { mutableStateOf("") }
    var password = remember { mutableStateOf("") }
    var showDialog = remember { mutableStateOf(false) }



    LaunchedEffect(state) {
        when (state) {

            is UiState.Success -> {
                  val user = state.user
                Log.d("", "LoginSuccess${user.name} ${user.email}")
                navController.navigate(Screen.HomeScreen.route)
            }

            is UiState.Error -> {
                showDialog.value = true
            }

            else -> {}
        }
    }


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
                    Spacer(Modifier.height(10.dp))

                    Text(
                        text = stringResource(id =R.string.btn_login ),
                        color = Color.Black,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(Modifier.height(40.dp))
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
                    Spacer(Modifier.height(20.dp))
                    Text(
                        stringResource(id = R.string.text_register_user),
                        color = color_write,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.clickable {
                            navController.navigate(Screen.Register.route)
                        }
                    )

                }
            }
        }



        if (state is UiState.Loading) {
            LoadingScreen()
        }

        CustomAlertDialog(
            show = showDialog.value,
            title = "Aviso",
            subtitle = "Ocurrio un error intenta nuevamente valida los datos ingresados",
            buttonText = "Aceptar",
            onDismiss = {
                showDialog.value = false
            },
            onConfirm = {}
        )
    }
}