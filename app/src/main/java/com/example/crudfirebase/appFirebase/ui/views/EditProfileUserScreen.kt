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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.crudfirebase.R
import com.example.crudfirebase.appFirebase.data.model.UserModel
import com.example.crudfirebase.appFirebase.ui.components.EmailInputField
import com.example.crudfirebase.appFirebase.ui.components.GenericInputField
import com.example.crudfirebase.appFirebase.ui.components.InputFieldGeneric
import com.example.crudfirebase.appFirebase.ui.components.PasswordInputField
import com.example.crudfirebase.appFirebase.ui.components.SlideToConfirmButton
import com.example.crudfirebase.appFirebase.viewmodel.AuthViewModel
import com.example.crudfirebase.appFirebase.viewmodel.UiState
import com.example.crudfirebase.ui.theme.color_write
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.delay
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileUserScreen(navController: NavController){


    val viewModel: AuthViewModel = hiltViewModel()
    val state = viewModel.state.value

    val snackbarHostState = remember {
        SnackbarHostState()
    }
    var phone = remember { mutableStateOf("") }
    var gender = remember { mutableStateOf("") }
    var birthdate = remember { mutableStateOf("") }
    var email = remember { mutableStateOf("") }
    var nameUser = remember { mutableStateOf("") }
    var showDatePicker = remember { mutableStateOf(false) }

    var originalName = remember { mutableStateOf("") }
    var originalPhone = remember { mutableStateOf("") }
    var originalEmail = remember { mutableStateOf("") }
    var originalBirthdate = remember { mutableStateOf("") }
    var originalGender = remember { mutableStateOf("") }

    var userLogin = remember { mutableStateOf<UserModel?>(null) }
    val uid = FirebaseAuth
        .getInstance()
        .currentUser
        ?.uid

    LaunchedEffect(Unit) {
        if (uid == null) return@LaunchedEffect

        val document = FirebaseFirestore.getInstance()
            .collection("users")
            .document(uid)
            .get()
            .await()

        val user = document.toObject(UserModel::class.java)

        user?.let {
            nameUser.value = it.name ?: ""
            phone.value = it.phone ?: ""
            email.value = it.email ?: ""
            birthdate.value = it.birthdate ?: ""
            gender.value = it.gender ?: ""

            originalName.value = it.name ?: ""
            originalPhone.value = it.phone ?: ""
            originalEmail.value = it.email ?: ""
            originalBirthdate.value = it.birthdate ?: ""
            originalGender.value = it.gender ?: ""
        }
    }


    /**Validate imputs**/
    val isFormValid =
        nameUser.value.isNotBlank() &&
                phone.value.isNotBlank() &&
                email.value.isNotBlank() &&
                birthdate.value.isNotBlank() &&
                gender.value.isNotBlank()

    /**validate input change**/
    val hasChanges =
        nameUser.value != originalName.value ||
                phone.value != originalPhone.value ||
                email.value != originalEmail.value ||
                birthdate.value != originalBirthdate.value ||
                gender.value != originalGender.value

    Log.d("", "DataUser: ${userLogin.value}")


    LaunchedEffect(state) {
        when (state) {
            is UiState.Success -> {
                snackbarHostState.showSnackbar(
                    "Datos actualizados correctamente"
                )
            }

            is UiState.Error -> {
                snackbarHostState.showSnackbar(
                    state.message
                )
            }
            else -> {}
        }
    }

    Scaffold (snackbarHost = {
        SnackbarHost(hostState = snackbarHostState)
    }){ innerPadding ->
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
                    text = stringResource(id = R.string.text_update_info),
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
                Spacer(Modifier.height(16.dp))
                InputFieldGeneric(
                    maxDigit = 10,
                    typeKeyboardType = KeyboardType.Phone,
                    icon = Icons.Default.Phone,
                    value = phone.value,
                    onValueChange = { phone.value = it },
                    placeholder = stringResource(id = R.string.text_phone),
                    isFocused = false,
                    isError = false
                )
                Spacer(Modifier.height(16.dp))
                InputFieldGeneric(
                    maxDigit = 20,
                    typeKeyboardType = KeyboardType.Text,
                    icon = Icons.Default.DateRange,
                    value = birthdate.value,
                    onValueChange = {
                        birthdate.value = it
                    },
                    placeholder = stringResource(id = R.string.text_birthdate),
                    isReadOnly = true,
                    onClick = {
                        showDatePicker.value = true
                    }
                )
                Spacer(Modifier.height(16.dp))
                EmailInputField(
                    value = email.value,
                    onValueChange = { email.value = it },
                    placeholder = stringResource(id = R.string.text_email_user)
                )
                Spacer(Modifier.height(16.dp))
                Column {
                    getGender(gender)
                }
                Spacer(Modifier.height(16.dp))
                SlideToConfirmButton(
                    text = stringResource(id = R.string.text_update),
                    enabled = isFormValid && hasChanges,
                    onComplete = {
                        viewModel.updateUser(
                            uid = uid.toString(),
                            email = email.value,
                            name = nameUser.value,
                            phone = phone.value,
                            gender = gender.value,
                            birthdate = birthdate.value,
                        )
                    }
                )

                if (showDatePicker.value) {

                    val datePickerState = rememberDatePickerState()

                    DatePickerDialog(
                        onDismissRequest = {
                            showDatePicker.value = false
                        },
                        confirmButton = {

                            Text(
                                text = "Aceptar",
                                fontWeight = FontWeight.Medium,
                                fontSize = 24.sp,
                                modifier = Modifier
                                    .padding(end = 20.dp, bottom = 15.dp)
                                    .clickable {

                                        val millis =
                                            datePickerState.selectedDateMillis

                                        birthdate.value =
                                            millis?.let {
                                                SimpleDateFormat(
                                                    "dd/MM/yyyy",
                                                    Locale.getDefault()
                                                )
                                                    .format(Date(it))
                                            } ?: ""

                                        showDatePicker.value = false
                                    }
                            )
                        }
                    ) {

                        DatePicker(
                            state = datePickerState
                        )
                    }
                }
                Spacer(Modifier.height(20.dp))

            }
        }
    }
}