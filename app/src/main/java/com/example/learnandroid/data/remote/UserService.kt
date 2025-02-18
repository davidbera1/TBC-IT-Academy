package com.example.learnandroid.data.remote

import com.example.learnandroid.data.model.UsersDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface UserService {
    @GET("users")
    suspend fun getUsers(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): Response<UsersDto>
}
