package com.example.crudfirebase.appFirebase.data.remote

import com.example.crudfirebase.appFirebase.data.model.UserModel
import com.google.firebase.auth.FirebaseAuth

class FirebaseAuthService {
    private val auth = FirebaseAuth.getInstance()

    fun loginUser(
        email: String,
        password: String,
        onResult: (UserModel?, String?) -> Unit
    ) {
        auth.signInWithEmailAndPassword(email, password).addOnSuccessListener { result ->
            val user = result.user
            onResult(user?.let {
                UserModel(
                    uid = it.uid,
                    email = it.email
                )
            }, null)
        }.addOnFailureListener {
            onResult(null, it.message)
        }
    }

    fun registerUser(
        email: String,
        password: String,
        onResult: (UserModel?, String?) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener { result ->
                val user = result.user
                onResult(
                    user?.let {
                        UserModel(it.uid, it.email)
                    },
                    null
                )
            }
            .addOnFailureListener {
                onResult(null, it.message)
            }
    }
}