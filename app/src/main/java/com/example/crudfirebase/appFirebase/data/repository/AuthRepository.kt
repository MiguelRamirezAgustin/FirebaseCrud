package com.example.crudfirebase.appFirebase.data.repository

import com.example.crudfirebase.appFirebase.data.model.UserModel
import com.example.crudfirebase.appFirebase.data.remote.FirebaseAuthService

class AuthRepository(private val service: FirebaseAuthService) {


    /**
     * Fun para Login usuario
     * **/
    fun login(email:String,
              password:String,
              onResul:(UserModel?, String?) -> Unit){
        service.loginUser(email, password, onResul)
    }

    /**
     * Fun para registar usuario
     * **/
    fun register(
        email: String,
        password: String,
        name: String,
        onResult: (UserModel?, String?) -> Unit
    ) {
        service.registerUser(email, password, name,onResult)
    }
}