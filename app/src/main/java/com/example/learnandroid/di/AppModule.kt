package com.example.learnandroid.di

import android.app.Application
import android.content.Context
import com.example.learnandroid.data.local.datastore.UserSessionManager
import com.example.learnandroid.data.local.room.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideUserSessionManager(context: Context): UserSessionManager {
        return UserSessionManager(context)
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
