package com.example.learnandroid.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.learnandroid.model.dataclass.UserSession
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "UserSession")

object UserSessionManager {

    private val IS_LOGGED_IN = booleanPreferencesKey("isLoggedIn")
    private val EMAIL = stringPreferencesKey("email")
    private val TOKEN = stringPreferencesKey("token")

    suspend fun saveUserSession(context: Context, userSession: UserSession) {
        withContext(Dispatchers.IO) {
            context.dataStore.edit { preferences ->
                preferences[IS_LOGGED_IN] = userSession.isLoggedIn
                preferences[EMAIL] = userSession.email ?: ""
                preferences[TOKEN] = userSession.token ?: ""
            }
        }
    }

    fun getUserSession(context: Context): Flow<UserSession> {
        return context.dataStore.data.map { preferences ->
            UserSession(
                isLoggedIn = preferences[IS_LOGGED_IN] ?: false,
                email = preferences[EMAIL],
                token = preferences[TOKEN]
            )
        }
    }

    suspend fun clearUserSession(context: Context) {
        withContext(Dispatchers.IO) {
            context.dataStore.edit { preferences ->
                preferences.clear()
            }
        }
    }
}