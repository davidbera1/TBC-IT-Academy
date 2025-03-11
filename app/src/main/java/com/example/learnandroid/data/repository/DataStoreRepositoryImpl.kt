package com.example.learnandroid.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.example.learnandroid.domain.repository.DataStoreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStoreRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : DataStoreRepository {
    override suspend fun <T> saveValue(key: Preferences.Key<T>, value: T) {
        dataStore.edit { preference ->
            preference[key] = value
        }
    }

    override fun <T> readValue(key: Preferences.Key<T>, defaultValue: T): Flow<T> {
        return dataStore.data.map {
            it[key] ?: defaultValue
        }
    }

    override suspend fun <T> removeByKey(key: Preferences.Key<T>) {
        dataStore.edit {
            it.remove(key)
        }
    }
}