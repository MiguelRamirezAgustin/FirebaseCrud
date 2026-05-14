package com.example.crudfirebase.appFirebase.notifications

import android.app.NotificationManager
import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows.shadowOf


@RunWith(RobolectricTestRunner::class)
class NotificationHelperTest {

    private lateinit var context: Context

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
    }

    @Test
    fun shouldShowNotification() {

        val helper = NotificationHelper(context)

        helper.showNotification(
            "Hola",
            "Mensaje"
        )

        val manager =
            context.getSystemService(
                Context.NOTIFICATION_SERVICE
            ) as NotificationManager

        val notifications =
            shadowOf(manager).allNotifications

        assertThat(notifications.size)
            .isEqualTo(1)
    }
}