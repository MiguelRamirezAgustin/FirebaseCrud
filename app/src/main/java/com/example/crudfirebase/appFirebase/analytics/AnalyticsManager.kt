package com.example.crudfirebase.appFirebase.analytics

import android.content.Context
import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics

class AnalyticsManager(context: Context) {

    private val analytics =
        FirebaseAnalytics.getInstance(context)

    /** Login */
    fun logLogin(user:String){
        val bundle = Bundle().apply {

            putString(
                "user_name",
                user
            )
        }

        analytics.logEvent(
            FirebaseAnalytics.Event.LOGIN,
            bundle
        )
    }

    /** Pantallas */
    fun logScreen(screenName: String){

        val bundle = Bundle().apply {

            putString(
                FirebaseAnalytics.Param.SCREEN_NAME,
                screenName
            )

            putString(
                FirebaseAnalytics.Param.SCREEN_CLASS,
                screenName
            )
        }

        analytics.logEvent(
            FirebaseAnalytics.Event.SCREEN_VIEW,
            bundle
        )
    }

    /** Evento personalizado */
    fun logCustomEvent(event: String){

        analytics.logEvent(event, null)
    }

    /** Producto */
    fun logBurgerSelected(name: String){

        val bundle = Bundle().apply {

            putString(
                "product_name",
                name
            )
        }

        analytics.logEvent(
            "selected_product",
            bundle
        )
    }
}