package com.example.learnandroid.data.remote

import com.example.learnandroid.data.model.CurrencyDto
import retrofit2.http.GET

interface CurrencyService {
    @GET("591e8a89-619b-4d48-8c3f-64b61d2d98bc")
    suspend fun getCurrency(): CurrencyDto
}