package com.example.crudfirebase.appFirebase.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.crudfirebase.appFirebase.data.model.UserModel
import com.example.crudfirebase.appFirebase.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    private val _state = mutableStateOf<UiState>(UiState.Idle)
    val state = _state

    /** Login **/
    fun login(email: String, password: String) {
        state.value = UiState.Loading

        repository.login(email, password) { result, errorMsg ->

            state.value = if (result != null) {
                UiState.Success(result)
            } else {
                UiState.Error(errorMsg ?: "Error desconocido")
            }
        }
    }

    /** Register **/
    fun registerUser(email: String, password: String,name:String, phone:String, gender:String, birthdate:String ) {
        state.value = UiState.Loading

        repository.register(email, password ,name, phone, gender, birthdate ) { result, errorMsg ->

            state.value = if (result != null) {
                UiState.Success(result)
            } else {
                UiState.Error(errorMsg ?: "Error desconocido")
            }
        }
    }
}