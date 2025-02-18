package com.example.learnandroid.di

import android.app.Application
import android.content.Context
import com.example.learnandroid.data.local.datastore.UserSessionManager
import com.example.learnandroid.data.local.room.AppDatabase
import com.example.learnandroid.data.remote.AuthService
import com.example.learnandroid.data.remote.RetrofitClient
import com.example.learnandroid.data.remote.UserService
import com.example.learnandroid.data.repository.UserRepository
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
    fun provideRetrofitClient(): RetrofitClient {
        return RetrofitClient()
    }

    @Provides
    @Singleton
    fun provideAuthService(retrofitClient: RetrofitClient): AuthService {
        return retrofitClient.provideAuthService()
    }

    @Provides
    @Singleton
    fun provideUserService(retrofitClient: RetrofitClient): UserService {
        return retrofitClient.provideUserService()
    }

    @Provides
    @Singleton
    fun provideUserRepository(
        authService: AuthService,
    ): UserRepository {
        return UserRepository(authService = authService)
    }

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
