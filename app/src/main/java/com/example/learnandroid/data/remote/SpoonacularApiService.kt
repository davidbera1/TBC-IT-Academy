package com.example.learnandroid.data.remote

import com.example.learnandroid.BuildConfig
import com.example.learnandroid.data.model.RandomRecipesDto
import com.example.learnandroid.data.model.RecipeDto
import com.example.learnandroid.data.model.SearchDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SpoonacularApiService {
    @GET("recipes/random")
    suspend fun getRandomRecipes(
        @Query("number") number: Int,
        @Query("apiKey") apiKey: String = BuildConfig.API_KEY
    ): Response<RandomRecipesDto>

    @GET("food/search")
    suspend fun searchFoodByName(
        @Query("query") query: String,
        @Query("apiKey") apiKey: String = BuildConfig.API_KEY
    ) : Response<SearchDto>

    @GET("recipes/{id}/information")
    suspend fun searchFoodById(
        @Path("id") id: Int,
        @Query("apiKey") apiKey: String = BuildConfig.API_KEY
    ) : Response<RecipeDto>

    @GET("recipes/informationBulk")
    suspend fun searchFoodsByIds(
        @Query("ids") ids: String,
        @Query("apiKey") apiKey: String = BuildConfig.API_KEY
    ) : Response<List<RecipeDto>>
}