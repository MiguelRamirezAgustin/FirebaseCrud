package com.example.crudfirebase.appFirebase.viewmodel

import com.example.crudfirebase.appFirebase.data.model.UserModel

sealed class UiState {
    object Idle : UiState()
    object Loading : UiState()
    data class Success(val user: UserModel) : UiState()
    data class Error(val message: String) : UiState()
    data class Message(val message: String) : UiState()
}