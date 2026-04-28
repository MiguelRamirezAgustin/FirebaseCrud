package com.example.crudfirebase.appFirebase.data.repository

import com.example.crudfirebase.appFirebase.data.model.UserModel
import com.example.crudfirebase.appFirebase.data.remote.FirebaseAuthService

class AuthRepository(private val service: FirebaseAuthService) {

    fun login(email:String,
              password:String,
              onResul:(UserModel?, String?) -> Unit){
        service.loginUser(email, password, onResul)
    }

    fun register(
        email: String,
        password: String,
        onResult: (UserModel?, String?) -> Unit
    ) {
        service.registerUser(email, password, onResult)
    }
}