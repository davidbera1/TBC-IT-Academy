package com.example.learnandroid.data.remote

import com.example.learnandroid.BuildConfig
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

class RetrofitClient {

    private val json = Json { ignoreUnknownKeys = true }

    private val client = OkHttpClient.Builder().apply {
        if(BuildConfig.DEBUG) {
            val interceptor = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
            addInterceptor(interceptor)
        }
    }.build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .client(client)
        .build()

    fun provideAuthService(): AuthService = retrofit.create(AuthService::class.java)

    fun provideUserService(): UserService = retrofit.create(UserService::class.java)
}