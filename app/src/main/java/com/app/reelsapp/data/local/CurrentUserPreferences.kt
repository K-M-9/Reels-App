package com.app.reelsapp.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.firstOrNull

object CurrentUserPreferences {
    private val Context.dataStore by preferencesDataStore(name = "user_prefs")

    private val USERNAME_KEY = stringPreferencesKey("username")

    suspend fun saveUsername(context: Context, username: String) {
        context.dataStore.edit { prefs ->
            prefs[USERNAME_KEY] = username
        }
    }

    suspend fun getUsername(context: Context): String? {
        return context.dataStore.data
            .firstOrNull()?.get(USERNAME_KEY)
    }

    suspend fun clear(context: Context) {
        context.dataStore.edit { prefs ->
            prefs.clear()
        }
    }
}

