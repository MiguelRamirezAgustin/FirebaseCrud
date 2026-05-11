package com.example.crudfirebase.appFirebase.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.crudfirebase.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {

        super.onNewToken(token)
        Log.d("FCM_TOKEN", "mytoken--> $token")

        val uid = FirebaseAuth
            .getInstance()
            .currentUser
            ?.uid

        if(uid != null){

            FirebaseFirestore.getInstance()
                .collection("users")
                .document(uid)
                .update(
                    "fcmToken",
                    token
                )
                .addOnSuccessListener {

                    Log.d(
                        "FCM_TOKEN",
                        "Token guardado"
                    )
                }
                .addOnFailureListener {

                    Log.d(
                        "FCM_TOKEN",
                        "Error guardando token"
                    )
                }
        }
    }

    override fun onMessageReceived(
        remoteMessage: RemoteMessage
    ) {

        super.onMessageReceived(remoteMessage)

        remoteMessage.notification?.let {

            showNotification(
                it.title ?: "",
                it.body ?: ""
            )
        }
    }

    private fun showNotification(
        title: String,
        message: String
    ){

        val channelId = "push_notification"

        val notificationManager =
            getSystemService(
                Context.NOTIFICATION_SERVICE
            ) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            val channel = NotificationChannel(
                channelId,
                "Push Notifications",
                NotificationManager.IMPORTANCE_HIGH
            )

            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(
            this,
            channelId
        )
            .setContentTitle(title)
            .setContentText(message)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(
            1,
            notification
        )
    }
}