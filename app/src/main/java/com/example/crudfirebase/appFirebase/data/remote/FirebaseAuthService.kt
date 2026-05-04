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
            val firebaseUser = result.user ?: return@addOnSuccessListener

            FirebaseFirestore.getInstance()
                .collection("users")
                .document(firebaseUser.uid)
                .get()
                .addOnSuccessListener { document ->

                    val user = document.toObject(UserModel::class.java)

                    onResult(user, null)
                }
                .addOnFailureListener {
                    onResult(null, it.message)
                }
        }.addOnFailureListener {
            onResult(null, it.message)
        }
    }

    /**Logica para registrar usuario**/
    fun registerUser(
        email: String,
        password: String,
        name:String,
        phone:String,
        gender:String,
        birthdate:String,
        onResult: (UserModel?, String?) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener { result ->
                val firebaseUser = result.user ?: return@addOnSuccessListener

                val user = UserModel(
                    uid = firebaseUser.uid,
                    email = firebaseUser.email ?: "",
                    name = name,
                    phone = phone,
                    gender = gender,
                    birthdate =birthdate
                )

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
            .addOnFailureListener {
                onResult(null, it.message)
            }
    }
}