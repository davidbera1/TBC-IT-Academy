package com.example.learnandroid.data.local.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavoritesManager @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    private val INT_LIST_KEY = stringSetPreferencesKey("int_list_key")

    suspend fun saveFavoriteId(favoriteId: Int) {
        initializeIntListIfDoesNotExist()

        dataStore.edit { preferences ->
            val existingSet = preferences[INT_LIST_KEY] ?: emptySet()
            val updatedSet = existingSet.toMutableSet().apply {
                add(favoriteId.toString())
            }
            preferences[INT_LIST_KEY] = updatedSet
        }
    }

    suspend fun removeFavoriteId(favoriteId: Int) {
        dataStore.edit { preferences ->
            val existingSet = preferences[INT_LIST_KEY] ?: emptySet()
            val updatedSet = existingSet.toMutableSet().apply {
                remove(favoriteId.toString())
            }
            preferences[INT_LIST_KEY] = updatedSet
        }
    }

    fun getFavoriteIdList(): Flow<List<Int>> {
        return dataStore.data.map { preferences ->
            (preferences[INT_LIST_KEY] ?: emptySet()).map { it.toInt() }
        }
    }

    private suspend fun initializeIntListIfDoesNotExist() {
        dataStore.edit { preferences ->
            if (preferences[INT_LIST_KEY] == null) {
                preferences[INT_LIST_KEY] = emptySet()
            }
        }
    }
}