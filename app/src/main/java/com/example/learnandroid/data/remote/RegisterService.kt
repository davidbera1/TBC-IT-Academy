package com.example.learnandroid.data.remote

import com.example.learnandroid.data.model.AuthDto
import com.example.learnandroid.data.model.RegisterResponseDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RegisterService {
    @POST("register")
    suspend fun register(@Body authDto: AuthDto): Response<RegisterResponseDto>
}