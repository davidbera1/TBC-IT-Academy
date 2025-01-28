package com.example.learnandroid.client

import com.example.learnandroid.model.dto.UsersDto
import retrofit2.Response
import retrofit2.http.GET

interface UserService {
    @GET("users?page=1")
    suspend fun getUsers(): Response<UsersDto>
}