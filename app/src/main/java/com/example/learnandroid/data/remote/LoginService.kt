package com.example.learnandroid.data.remote

import com.example.learnandroid.data.model.AuthDto
import com.example.learnandroid.data.model.LoginResponseDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {
    @POST("login")
    suspend fun login(@Body authDto: AuthDto): Response<LoginResponseDto>
}