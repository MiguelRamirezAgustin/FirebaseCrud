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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.example.crudfirebase.appFirebase.ui.components.GenericInputField
import com.example.crudfirebase.appFirebase.ui.components.PasswordInputField
import com.example.crudfirebase.appFirebase.ui.components.SlideToConfirmButton
import com.example.crudfirebase.appFirebase.viewmodel.AuthViewModel
import com.example.crudfirebase.appFirebase.viewmodel.UiState
import com.example.crudfirebase.ui.theme.color_write


@Composable
fun RegisterUserScreen(navController: NavHostController) {

    val viewModel: AuthViewModel = hiltViewModel()
    val state = viewModel.state.value
    var showDialog = remember { mutableStateOf(false) }
    var email = remember { mutableStateOf("") }
    var nameUser = remember { mutableStateOf("") }
    var password = remember { mutableStateOf("") }

    fun hasUpperCase(password: String): Boolean {
        return password.any { it.isUpperCase() }
    }

    fun hasLowerCase(password: String): Boolean {
        return password.any { it.isLowerCase() }
    }

    fun hasNumber(password: String): Boolean {
        return password.any { it.isDigit() }
    }

    fun hasSymbol(password: String): Boolean {
        return password.any { !it.isLetterOrDigit() }
    }

    fun hasMinLength(password: String): Boolean {
        return password.length >= 8
    }

    LaunchedEffect(state) {
        if (state is UiState.Success) {
            showDialog.value = true
        }
    }

    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    modifier = Modifier
                        .size(24.dp)
                        .align(Alignment.CenterStart)
                        .clickable {
                            navController.popBackStack()
                        }
                )
            }
        },
        modifier = Modifier.fillMaxSize()
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
                    text = stringResource(id = R.string.text_register),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(Modifier.height(24.dp))
                GenericInputField(
                    isIcon = Icons.Default.Person,
                    value = nameUser.value,
                    onValueChange = { nameUser.value = it },
                    placeholder = stringResource(id = R.string.text_name_user),
                    isFocused = false,
                    isError = false
                )

                Spacer(Modifier.height(24.dp))
                EmailInputField(
                    value = email.value,
                    onValueChange = { email.value = it },
                    placeholder = stringResource(id = R.string.text_email_user)
                )

                Spacer(Modifier.height(16.dp))
                PasswordInputField(
                    value = password.value,
                    onValueChange = { password.value = it },
                    placeholder = stringResource(id = R.string.text_password)
                )
                if (password.value.isNotEmpty()) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp)
                    ) {
                        PasswordRequirement(
                            text = "Una letra mayúscula",
                            isValid = hasUpperCase(password.value)
                        )

                        PasswordRequirement(
                            text = "Una letra minúscula",
                            isValid = hasLowerCase(password.value)
                        )

                        PasswordRequirement(
                            text = "Un número",
                            isValid = hasNumber(password.value)
                        )

                        PasswordRequirement(
                            text = "Un símbolo",
                            isValid = hasSymbol(password.value)
                        )

                        PasswordRequirement(
                            text = "Mínimo 8 caracteres",
                            isValid = hasMinLength(password.value)
                        )
                    }
                }

                Spacer(Modifier.height(35.dp))
                SlideToConfirmButton(
                    text = stringResource(id = R.string.text_register_user_screen),
                    enabled = email.value.isNotEmpty() && password.value.isNotEmpty(),
                    onComplete = {
                        viewModel.registerUser(email.value, password.value,nameUser.value)
                    }
                )

                Spacer(Modifier.height(20.dp))

                if (state is UiState.Error) {
                    Text(
                        text = state.message,
                        color = Color.Red
                    )
                }
            }
        }
    }

    if (state is UiState.Loading) {
        LoadingScreen()
    }

    CustomAlertDialog(
        show = showDialog.value,
        title = "Registro exitoso",
        subtitle = "Usuario creado correctamente\n${email.value}",
        buttonText = "Aceptar",
        onDismiss = {
            showDialog.value = false
            navController.navigate(Screen.HomeScreen.route)
        },
        onConfirm = {}
    )
}



/**fun to validate password**/
@Composable
fun PasswordRequirement(
    text: String,
    isValid: Boolean
) {
    Text(
        text = if (isValid) "✔ $text" else "✘ $text",
        color = if (isValid) Color(0xFF4CAF50) else Color.Red,
        fontSize = 12.sp,
        modifier = Modifier.padding(vertical = 2.dp, horizontal = 18.dp)
    )
}