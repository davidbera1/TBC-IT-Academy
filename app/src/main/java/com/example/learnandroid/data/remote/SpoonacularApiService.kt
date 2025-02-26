package com.example.learnandroid.data.remote

import com.example.learnandroid.BuildConfig
import com.example.learnandroid.data.model.RandomRecipesDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SpoonacularApiService {
    @GET("recipes/random")
    suspend fun getRandomRecipes(
        @Query("number") number: Int,
        @Query("apiKey") apiKey: String = BuildConfig.API_KEY
    ): Response<RandomRecipesDto>
}