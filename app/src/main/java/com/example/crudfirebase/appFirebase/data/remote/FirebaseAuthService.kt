package com.example.crudfirebase.appFirebase.data.remote

import com.example.crudfirebase.appFirebase.data.model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class FirebaseAuthService {
    private val auth = FirebaseAuth.getInstance()

    /**Logica para login de usuario**/
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

    /**Logica para registrar usuario**/
    fun registerUser(
        email: String,
        password: String,
        onResult: (UserModel?, String?) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener { result ->
                val firebaseUser = result.user

                if (firebaseUser != null) {

                    val user = UserModel(
                        uid = firebaseUser.uid,
                        email = firebaseUser.email ?: ""
                    )
                      /***Almacenar usuario*/
                    FirebaseFirestore.getInstance()
                        .collection("users")
                        .document(user.uid)
                        .set(user)
                        .addOnSuccessListener {
                            onResult(user, null)
                        }
                        .addOnFailureListener {
                            onResult(null, it.message)
                        }

                }
            }
            .addOnFailureListener {
                onResult(null, it.message)
            }
    }
}