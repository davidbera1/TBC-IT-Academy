package com.example.learnandroid.client

import com.example.learnandroid.model.dto.UsersDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface UserService {
    @GET("users")
    suspend fun getUsers(@Query("page") page: Int): Response<UsersDto>
}
