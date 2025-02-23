package com.example.learnandroid.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

val Context.dataStore by preferencesDataStore(name = "settings")

@Singleton
class LanguagePreference @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    private val LANGUAGE_KEY = stringPreferencesKey("language_key")

    suspend fun saveLanguage(language: String) {
        dataStore.edit { preferences ->
            preferences[LANGUAGE_KEY] = language
        }
    }

    fun getLanguage(): Flow<String> = dataStore.data.map { preferences ->
        preferences[LANGUAGE_KEY] ?: "en"
    }
}