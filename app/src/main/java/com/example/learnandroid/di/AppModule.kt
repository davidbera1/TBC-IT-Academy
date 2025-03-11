package com.example.learnandroid.di

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.learnandroid.data.local.room.AppDatabase
import com.example.learnandroid.data.repository.DataStoreRepositoryImpl
import com.example.learnandroid.domain.repository.DataStoreRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "preferences")

    @Module
    @InstallIn(SingletonComponent::class)
    object DataStoreModule {

        @Provides
        @Singleton
        fun provideDataStore(context: Context): DataStore<Preferences> {
            return context.dataStore
        }

        @Provides
        @Singleton
        fun provideDataStoreRepository(dataStore: DataStore<Preferences>): DataStoreRepository {
            return DataStoreRepositoryImpl(dataStore)
        }
    }

    @Provides
    @Singleton
    fun provideContext(application: Application): Context {
        return application.applicationContext
    }

    @Provides
    @Singleton
    fun provideAppDatabase(context: Context): AppDatabase {
        return AppDatabase.DatabaseProvider.getDatabase(context)
    }
}
