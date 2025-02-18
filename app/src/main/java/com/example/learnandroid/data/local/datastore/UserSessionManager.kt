package com.example.learnandroid.data.local.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.learnandroid.data.model.UserSession
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "UserSession")

class UserSessionManager @Inject constructor(
    private val context: Context
) {
    private val IS_LOGGED_IN = booleanPreferencesKey("isLoggedIn")
    private val EMAIL = stringPreferencesKey("email")
    private val TOKEN = stringPreferencesKey("token")

    suspend fun saveUserSession(userSession: UserSession) {
        withContext(Dispatchers.IO) {
            context.dataStore.edit { preferences ->
                preferences[IS_LOGGED_IN] = userSession.isLoggedIn
                preferences[EMAIL] = userSession.email ?: ""
                preferences[TOKEN] = userSession.token ?: ""
            }
        }
    }

    fun getUserSession(): Flow<UserSession> {
        return context.dataStore.data.map { preferences ->
            UserSession(
                isLoggedIn = preferences[IS_LOGGED_IN] ?: false,
                email = preferences[EMAIL],
                token = preferences[TOKEN]
            )
        }
    }

    suspend fun clearUserSession() {
        withContext(Dispatchers.IO) {
            context.dataStore.edit { preferences ->
                preferences.clear()
            }
        }
    }
}