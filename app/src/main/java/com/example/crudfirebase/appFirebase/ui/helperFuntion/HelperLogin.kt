package com.example.crudfirebase.appFirebase.ui.helperFuntion



/**Fun validate emeal and password**/
fun isValidCredentials(
    email: String,
    password: String
): Boolean {

    val isValidEmail =
        android.util.Patterns.EMAIL_ADDRESS
            .matcher(email)
            .matches()

    val isValidPassword =
        password.length >= 8

    return isValidEmail && isValidPassword
}