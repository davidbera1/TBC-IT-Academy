package com.example.learnandroid.client

import com.example.learnandroid.model.dto.AuthDto
import com.example.learnandroid.model.dto.LoginResponseDto
import com.example.learnandroid.model.dto.RegisterResponseDto
import com.example.learnandroid.model.dto.UsersDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthService {
    @POST("login")
    suspend fun login(@Body authDto: AuthDto): Response<LoginResponseDto>

    @POST("register")
    suspend fun register(@Body authDto: AuthDto): Response<RegisterResponseDto>
}