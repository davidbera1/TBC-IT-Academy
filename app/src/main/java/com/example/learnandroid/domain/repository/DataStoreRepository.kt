package com.example.learnandroid.domain.repository

import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {
    suspend fun <T> saveValue(key: Preferences.Key<T>, value: T)

    fun <T> readValue(key: Preferences.Key<T>, defaultValue: T): Flow<T>

    suspend fun <T> removeByKey(key: Preferences.Key<T>)
}