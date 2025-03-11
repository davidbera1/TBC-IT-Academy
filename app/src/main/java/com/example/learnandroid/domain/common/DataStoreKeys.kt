package com.example.learnandroid.domain.common

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object DataStoreKeys {
    val IS_LOGGED_IN = booleanPreferencesKey("isLoggedIn")
    val EMAIL = stringPreferencesKey("email")
    val TOKEN = stringPreferencesKey("token")
}