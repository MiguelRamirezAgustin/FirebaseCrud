package com.example.crudfirebase

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import android.Manifest
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.example.crudfirebase.appFirebase.navigation.NavigationController
import com.example.crudfirebase.ui.theme.CrudFirebaseTheme
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint

class MainActivity : FragmentActivity() {

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted ->

            if (isGranted) {

                Log.d(
                    "NOTIFICATION",
                    "Permiso concedido"
                )

            } else {

                Log.d(
                    "NOTIFICATION",
                    "Permiso denegado"
                )
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        askNotificationPermission()

        FirebaseMessaging.getInstance().token
            .addOnSuccessListener { token ->

                Log.d(
                    "FCM_TOKEN",
                    "MainActivity Token $token"
                )
            }

        enableEdgeToEdge()

        setContent {

            CrudFirebaseTheme {

                NavigationController()
            }
        }
    }

    private fun askNotificationPermission() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {

            when {

                ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED -> {

                    Log.d(
                        "NOTIFICATION",
                        "Ya tiene permiso"
                    )
                }

                else -> {

                    requestPermissionLauncher.launch(
                        Manifest.permission.POST_NOTIFICATIONS
                    )
                }
            }
        }
    }
}