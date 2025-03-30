package com.example.learnandroid.data.remote

import com.example.learnandroid.data.model.CardDto
import retrofit2.Response
import retrofit2.http.GET

interface CardService {
    @GET("d689fe3e-6faf-446a-9896-c538de3449fa")
    suspend fun getCards(): Response<List<CardDto>>
}