package com.example.crudfirebase.appFirebase.ui.views

import android.util.Log
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity

object BiometricPromptManager {

    fun showBiometricPrompt(
        activity: FragmentActivity,
        onSuccess: () -> Unit
    ) {

        val executor =
            ContextCompat.getMainExecutor(activity)
        val biometricPrompt =
            BiometricPrompt(
                activity,
                executor,
                object : BiometricPrompt.AuthenticationCallback() {

                    override fun onAuthenticationSucceeded(
                        result: BiometricPrompt.AuthenticationResult
                    ) {
                        super.onAuthenticationSucceeded(result)

                        Log.d("BIOMETRIC", "SUCCESS")

                        onSuccess()
                    }

                    override fun onAuthenticationError(
                        errorCode: Int,
                        errString: CharSequence
                    ) {
                        super.onAuthenticationError(errorCode, errString)

                        Log.d(
                            "BIOMETRIC",
                            "ERROR: $errorCode - $errString"
                        )
                    }

                    override fun onAuthenticationFailed() {
                        super.onAuthenticationFailed()

                        Log.d(
                            "BIOMETRIC",
                            "FAILED"
                        )
                    }
                }
            )

        val promptInfo =
            BiometricPrompt.PromptInfo.Builder()
                .setTitle("Iniciar sesión")
                .setSubtitle("Utiliza tu huella digital")
                .setNegativeButtonText("Cancelar")
                .build()

        biometricPrompt.authenticate(promptInfo)
    }
}