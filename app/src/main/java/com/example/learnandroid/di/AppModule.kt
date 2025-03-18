package com.example.learnandroid.di

import com.example.learnandroid.BuildConfig
import com.example.learnandroid.data.remote.CategoryService
import com.example.learnandroid.data.repository.CategoryRepositoryImpl
import com.example.learnandroid.domain.repository.CategoryRepository
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    @Provides
    @Singleton
    fun provideCategoryService(retrofit: Retrofit) : CategoryService {
        return retrofit.create(CategoryService::class.java)
    }
}