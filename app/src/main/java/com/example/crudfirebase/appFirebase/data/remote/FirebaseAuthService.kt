package com.example.crudfirebase.appFirebase.data.remote

import android.util.Log
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

    /**fun update user**/
    fun updateUser(  uid:String,
                     email: String,
                     name:String,
                     phone:String,
                     gender:String,
                     birthdate:String,
                     onResult: (UserModel?, String?) -> Unit){
        val db = FirebaseFirestore.getInstance()

        val updates = mapOf(
            "email" to email,
            "name" to name,
            "phone" to phone,
            "gender" to gender,
            "birthdate" to birthdate
        )

        db.collection("users")
            .document(uid)
            .update(updates)
            .addOnSuccessListener {
                val user = UserModel(
                    uid = uid,
                    email = email,
                    name = name,
                    phone = phone,
                    gender = gender,
                    birthdate = birthdate
                )
                Log.d("Print Log ========>", " Update exitoso")
                onResult(user, null)
            }
            .addOnFailureListener {
                Log.d("Print Log ========>", " Error update")
                onResult(null, it.message)
            }
    }

    /**fun delete user**/
    fun deleteUser(uid: String,
                   onResult: (Boolean, String?) -> Unit){
        FirebaseFirestore.getInstance()
            .collection("users")
            .document(uid)
            .delete()
            .addOnSuccessListener {
                onResult(true, null)
            }
            .addOnFailureListener {

                onResult(false, it.message)
            }
    }

    /**fun get use list**/
    fun getUsers(
        onResult: (List<UserModel>) -> Unit
    ){

        FirebaseFirestore.getInstance()
            .collection("users")
            .get()
            .addOnSuccessListener { result ->
                val users = result.documents
                    .mapNotNull {
                        it.toObject(UserModel::class.java)
                    }

                onResult(users)
            }
    }
}