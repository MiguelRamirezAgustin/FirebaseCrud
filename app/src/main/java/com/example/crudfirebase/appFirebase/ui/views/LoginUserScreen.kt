package com.example.crudfirebase.appFirebase.ui.views

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.FragmentActivity
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.crudfirebase.R
import com.example.crudfirebase.appFirebase.analytics.AnalyticsManager
import com.example.crudfirebase.appFirebase.data.local.DataStoreManager
import com.example.crudfirebase.appFirebase.navigation.Screen
import com.example.crudfirebase.appFirebase.ui.components.CustomAlertDialog
import com.example.crudfirebase.appFirebase.ui.components.EmailInputField
import com.example.crudfirebase.appFirebase.ui.components.PasswordInputField
import com.example.crudfirebase.appFirebase.ui.components.SlideToConfirmButton
import com.example.crudfirebase.appFirebase.ui.helperFuntion.isValidCredentials
import com.example.crudfirebase.appFirebase.ui.utils.BiometricHelper
import com.example.crudfirebase.appFirebase.viewmodel.AuthViewModel
import com.example.crudfirebase.appFirebase.viewmodel.UiState
import com.example.crudfirebase.ui.theme.color_blue_backgraund
import com.example.crudfirebase.ui.theme.color_fondo_oscuro
import com.example.crudfirebase.ui.theme.color_write
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun LoginUserScreen(navController: NavHostController) {
    val context = LocalContext.current
    val viewModel: AuthViewModel = hiltViewModel()
    val isAnality = AnalyticsManager(context = context)
    val state = viewModel.state.value
    var email = remember { mutableStateOf("") }
    var password = remember { mutableStateOf("") }
    var showDialog = remember { mutableStateOf(false) }
    val biometricEnabled =
        DataStoreManager.getBiometricEnabled(context).collectAsState(initial = false)
    val activity = context as FragmentActivity
    var showEnableBiometricDialog = remember { mutableStateOf(false) }
    val savedEmail = DataStoreManager.getEmail(context).collectAsState(initial = "")
    val savedPassword = DataStoreManager.getPassword(context).collectAsState(initial = "")

    //miguel@gmial.com Miguel1993* UserAdmin

    LaunchedEffect(state) {
        when (state) {

            is UiState.Success -> {
                val user = state.user
                Log.d("", "LoginSuccess${user.name} ${user.email}")
                isAnality.logLogin(
                    "LoginSuccess " + user.name
                )
                FirebaseMessaging.getInstance().token
                    .addOnSuccessListener { token ->

                        FirebaseFirestore.getInstance()
                            .collection("users")
                            .document(user.uid)
                            .update("fcmToken", token)

                        Log.d("FCM_TOKEN", "Logintoken ${token}")
                    }


                if (
                    email.value.isNotBlank() &&
                    password.value.isNotBlank()
                ) {

                    DataStoreManager.saveEmail(
                        context,
                        email.value
                    )

                    DataStoreManager.savePassword(
                        context,
                        password.value
                    )

                    Log.d(
                        "DATASTORE",
                        "Credenciales guardadas"
                    )
                }

                if (!biometricEnabled.value) {
                    showEnableBiometricDialog.value = true
                } else {
                    navController.navigate(Screen.HomeScreen.route) {
                        popUpTo(Screen.Login.route) {
                            inclusive = true
                        }
                    }
                }
            }

            is UiState.Error -> {
                showDialog.value = true
            }

            else -> {}
        }
    }

    LaunchedEffect(biometricEnabled.value) {
        if (
            biometricEnabled.value &&
            BiometricHelper.isBiometricAvailable(context)
        ) {

            activity?.let {

                BiometricPromptManager.showBiometricPrompt(
                    activity = activity
                ) {
                    if (FirebaseAuth.getInstance().currentUser != null) {
                        navController.navigate(Screen.HomeScreen.route) {
                            popUpTo(Screen.Login.route) {
                                inclusive = true
                            }
                        }
                    }
                }
            }
        }
    }

    Scaffold(
        containerColor = color_fondo_oscuro,
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(
                            Color(0xFF0E6A63),
                            Color(0xFF2C2F39),
                            Color(0xFF23252D)
                        )
                    )
                )
                .padding(innerPadding)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 40.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Surface(
                    modifier = Modifier.size(95.dp),
                    shape = CircleShape,
                    color = Color(0xFF4D5663),
                    shadowElevation = 12.dp
                ) {

                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {

                        Icon(
                            painter = painterResource(R.drawable.iconfirebase),
                            contentDescription = null,
                            modifier = Modifier.size(55.dp),
                            tint = Color.Unspecified
                        )

                    }

                }

                Spacer(Modifier.height(20.dp))

                Text(
                    text = stringResource(R.string.btn_login),
                    color = Color.White,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Medium
                )
                Spacer(Modifier.height(40.dp))
                EmailInputField(
                    value = email.value,
                    onValueChange = { email.value = it },
                    placeholder = stringResource(id = R.string.text_email_user),
                    isFocused = false,
                    isError = false
                )

                Spacer(Modifier.height(16.dp))
                PasswordInputField(
                    value = password.value,
                    onValueChange = { password.value = it },
                    placeholder = stringResource(id = R.string.text_password),
                )


                Spacer(Modifier.height(35.dp))
                SlideToConfirmButton(
                    text = stringResource(id = R.string.text_aceptar),
                    enabled = isValidCredentials(email.value, password.value),
                    onComplete = {
                        isAnality.logScreen("LoginUser")
                        viewModel.login(email.value, password.value)
                    }
                )
                Spacer(Modifier.height(20.dp))
                Text(
                    stringResource(id = R.string.text_register_user),
                    color = Color.LightGray,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.clickable {
                        navController.navigate(Screen.Register.route)
                    }
                )

                Surface(
                    modifier = Modifier
                        .padding(top = 35.dp)
                        .size(90.dp),
                    shape = CircleShape,
                    shadowElevation = 2.dp,
                ) {
                    IconButton(
                        modifier = Modifier,
                        onClick = {
                            Log.d("BIOMETRIC", "Email=${savedEmail.value}")
                            Log.d("BIOMETRIC", "Password=${savedPassword.value}")

                            if (
                                savedEmail.value.isBlank() ||
                                savedPassword.value.isBlank()
                            ) {
                                Log.e("BIOMETRIC", "No hay credenciales guardadas")
                                return@IconButton
                            }

                            BiometricPromptManager.showBiometricPrompt(activity) {

                                viewModel.login(
                                    savedEmail.value,
                                    savedPassword.value
                                )
                            }
                        }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.huella_dactilar),
                            contentDescription = "Huella",
                            tint = Color.White,
                            modifier = Modifier.size(50.dp)
                        )
                    }
                }
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

    if (showEnableBiometricDialog.value) {

        AlertDialog(
            onDismissRequest = {},
            title = {
                Text("Activar huella")
            },
            text = {
                Text(
                    "¿Deseas iniciar sesión usando tu huella digital?"
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {

                        CoroutineScope(Dispatchers.IO).launch {
                            DataStoreManager.saveBiometricEnabled(
                                context,
                                true
                            )
                        }

                        showEnableBiometricDialog.value = false

                        navController.navigate(Screen.HomeScreen.route) {
                            popUpTo(Screen.Login.route) {
                                inclusive = true
                            }
                        }
                    }
                ) {
                    Text("Aceptar")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {

                        showEnableBiometricDialog.value = false

                        navController.navigate(Screen.HomeScreen.route) {
                            popUpTo(Screen.Login.route) {
                                inclusive = true
                            }
                        }
                    }
                ) {
                    Text("Ahora no")
                }
            }
        )
    }

}


fun Context.findActivity(): Activity? {
    return when (this) {
        is Activity -> this
        is ContextWrapper -> baseContext.findActivity()
        else -> null
    }
}