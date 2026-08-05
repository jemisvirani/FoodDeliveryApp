package com.order.food.auth.domain.util

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import jakarta.inject.Inject
import kotlinx.coroutines.flow.first

class UserPreferences @Inject constructor(private val context: Context) {

    private val Context.dataStore by preferencesDataStore("user_preferences")

    companion object {
        val REMEMBER_ME = booleanPreferencesKey("remember_me")
        val EMAIL = stringPreferencesKey("email")
    }

    suspend fun saveRememberMe(
        rememberMe: Boolean,
        email: String
    ) {
        context.dataStore.edit { pref ->
            pref[REMEMBER_ME] = rememberMe

            if (rememberMe) {
                pref[EMAIL] = email
            } else {
                pref.remove(EMAIL)
            }
        }
    }

    suspend fun clearRememberMe() {
        context.dataStore.edit {
            it.clear()
        }
    }

    suspend fun isRememberMe(): Boolean {
        return context.dataStore.data.first()[REMEMBER_ME] ?: false
    }

    suspend fun getEmail(): String {
        return context.dataStore.data.first()[EMAIL] ?: ""
    }
}