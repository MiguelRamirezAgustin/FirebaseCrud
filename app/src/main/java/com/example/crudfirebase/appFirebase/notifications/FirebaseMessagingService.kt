package com.example.crudfirebase.appFirebase.notifications

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


class MyFirebaseMessagingService : FirebaseMessagingService() {
    override fun onMessageReceived(
        remoteMessage: RemoteMessage
    ) {

        val title =
            remoteMessage.data["title"] ?: ""

        val body =
            remoteMessage.data["body"] ?: ""

        NotificationHelper(this)
            .showNotification(title, body)
    }
}