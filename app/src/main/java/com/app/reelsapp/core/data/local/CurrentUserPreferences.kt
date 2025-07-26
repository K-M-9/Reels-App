package com.app.reelsapp.core.data.local


import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

private val Context.dataStore by preferencesDataStore(name = "user_prefs")

class CurrentUserPreferences @Inject constructor(
    private val context: Context
) {
    private val USERNAME_KEY = stringPreferencesKey("username")

    suspend fun saveUsername(username: String) {
        context.dataStore.edit { prefs ->
            prefs[USERNAME_KEY] = username
        }
    }

    suspend fun getUsername(): String? {
        return context.dataStore.data.firstOrNull()?.get(USERNAME_KEY)
    }

    suspend fun clear() {
        context.dataStore.edit { prefs ->
            prefs.clear()
        }
    }
}