package com.example.learnandroid.client

import com.example.learnandroid.model.AuthDto
import com.example.learnandroid.model.LoginResponse
import com.example.learnandroid.model.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("login")
    suspend fun login(@Body authDto: AuthDto): Response<LoginResponse>

    @POST("register")
    suspend fun register(@Body authDto: AuthDto): Response<RegisterResponse>
}