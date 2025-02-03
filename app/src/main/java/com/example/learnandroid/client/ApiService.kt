package com.example.learnandroid.client

import com.example.learnandroid.model.ResponseDto
import retrofit2.Response
import retrofit2.http.POST

interface ApiService {
    @POST("f3f41821-7434-471f-9baa-ae3dee984e6d")
    suspend fun getUsers(): Response<ResponseDto>
}