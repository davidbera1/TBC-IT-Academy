package com.example.learnandroid.di

import com.example.learnandroid.data.remote.SpoonacularApiService
import com.example.learnandroid.data.remote.common.ApiHelper
import com.example.learnandroid.data.repository.SpoonacularRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideSpoonacularRepository(spoonacularApiService: SpoonacularApiService): SpoonacularRepositoryImpl {
        return SpoonacularRepositoryImpl(spoonacularApiService, ApiHelper())
    }
}