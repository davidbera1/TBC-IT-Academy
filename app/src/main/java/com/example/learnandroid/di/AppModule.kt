package com.example.learnandroid.di

import com.example.learnandroid.BuildConfig
import com.example.learnandroid.data.remote.AccountStatusService
import com.example.learnandroid.data.remote.CardService
import com.example.learnandroid.data.remote.CurrencyService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
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
    fun provideCardService(retrofit: Retrofit): CardService {
        return retrofit.create(CardService::class.java)
    }

    @Provides
    @Singleton
    fun provideCurrencyService(retrofit: Retrofit): CurrencyService {
        return retrofit.create(CurrencyService::class.java)
    }

    @Provides
    @Singleton
    fun provideAccountStatusService(retrofit: Retrofit): AccountStatusService {
        return retrofit.create(AccountStatusService::class.java)
    }
}