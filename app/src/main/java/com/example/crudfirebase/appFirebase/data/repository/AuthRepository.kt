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
        phone:String,
        gender:String,
        birthdate:String,
        onResult: (UserModel?, String?) -> Unit,

    ) {
        service.registerUser(email, password, name, phone, gender, birthdate ,onResult)
    }

    /**
     * Fun para actualizar usuario
     **/

    fun update( uid:String,
                email: String,
                name: String,
                phone:String,
                gender:String,
                birthdate:String,
                onResult: (UserModel?, String?) -> Unit){
        service.updateUser(uid,email, name, phone, gender, birthdate,onResult)
    }

    /***
     * Delete user
     * */
    fun deleteUser(uid: String,
                   onResult: (Boolean, String?) -> Unit){
        service.deleteUser(uid, onResult)
    }

    /**Get list user**/
    fun getUsers(
        onResult: (List<UserModel>) -> Unit
    ){
        service.getUsers(onResult)
    }
}