package com.example.crudfirebase.appFirebase.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.crudfirebase.appFirebase.data.model.UserModel
import com.example.crudfirebase.appFirebase.data.repository.AuthRepository

class AuthViewModel(
    private val repository: AuthRepository
) : ViewModel() {
    var user = mutableStateOf<UserModel?>(null)
    var error = mutableStateOf<String?>(null)
    var isLoading = mutableStateOf(false)

    fun login(email: String, password: String) {
        isLoading.value = true

        repository.login(email, password) { result, errorMsg ->
            isLoading.value = false

            if (result != null) {
                user.value = result
            } else {
                error.value = errorMsg
            }
        }
    }
}