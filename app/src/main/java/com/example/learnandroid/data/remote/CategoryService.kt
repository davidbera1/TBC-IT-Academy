package com.example.learnandroid.data.remote

import com.example.learnandroid.data.model.CategoryDto
import retrofit2.Response
import retrofit2.http.GET

interface CategoryService {
    @GET("499e0ffd-db69-4955-8d86-86ee60755b9c")
    suspend fun getCategories(): Response<List<CategoryDto>>
}