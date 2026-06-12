package com.example.crudfirebase.appFirebase.data.local


import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(
    name = "user_preferences"
)

object DataStoreManager {

    private val BIOMETRIC_ENABLED =
        booleanPreferencesKey("biometric_enabled")

    private val EMAIL =
        stringPreferencesKey("email")

    private val PASSWORD =
        stringPreferencesKey("password")

    suspend fun saveBiometricEnabled(
        context: Context,
        enabled: Boolean
    ) {
        context.dataStore.edit { preferences ->
            preferences[BIOMETRIC_ENABLED] = enabled
        }
    }

    fun getBiometricEnabled(
        context: Context
    ): Flow<Boolean> {

        return context.dataStore.data.map { preferences ->
            preferences[BIOMETRIC_ENABLED] ?: false
        }
    }



    // Guardar email
    suspend fun saveEmail(
        context: Context,
        email: String
    ) {
        context.dataStore.edit { preferences ->
            preferences[EMAIL] = email
        }
    }

    // Guardar password
    suspend fun savePassword(
        context: Context,
        password: String
    ) {
        context.dataStore.edit { preferences ->
            preferences[PASSWORD] = password
        }
    }

    // Obtener email
    fun getEmail(
        context: Context
    ): Flow<String> {
        return context.dataStore.data.map { preferences ->
            preferences[EMAIL] ?: ""
        }
    }

    // Obtener password
    fun getPassword(
        context: Context
    ): Flow<String> {
        return context.dataStore.data.map { preferences ->
            preferences[PASSWORD] ?: ""
        }
    }
}