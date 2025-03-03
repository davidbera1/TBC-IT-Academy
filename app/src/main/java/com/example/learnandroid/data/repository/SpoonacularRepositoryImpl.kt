package com.example.learnandroid.data.repository

import com.example.learnandroid.data.model.RandomRecipesDto
import com.example.learnandroid.data.model.RecipeDto
import com.example.learnandroid.data.remote.SpoonacularApiService
import com.example.learnandroid.data.remote.common.ApiHelper
import com.example.learnandroid.data.remote.common.Resource
import com.example.learnandroid.data.model.SearchDto
import javax.inject.Inject

class SpoonacularRepositoryImpl @Inject constructor(
    private val spoonacularApiService: SpoonacularApiService,
    private val apiHelper: ApiHelper
) : SpoonacularRepository {

    override suspend fun getRandomRecipes(number: Int): RandomRecipesDto {
        val result = apiHelper.handleHttpRequest { spoonacularApiService.getRandomRecipes(number) }
        return when (result) {
            is Resource.Success -> result.data
            is Resource.Error -> throw Throwable(result.error)
        }
    }

    override suspend fun searchFoodByName(query: String): SearchDto {
        val result = apiHelper.handleHttpRequest { spoonacularApiService.searchFoodByName(query=query) }
        return when (result) {
            is Resource.Success -> result.data
            is Resource.Error -> throw Throwable(result.error)
        }
    }

    override suspend fun searchFoodById(id: Int): RecipeDto {
        val result = apiHelper.handleHttpRequest { spoonacularApiService.searchFoodById(id) }
        return when (result) {
            is Resource.Success -> result.data
            is Resource.Error -> throw Throwable(result.error)
        }
    }

    override suspend fun searchFoodsByIds(ids: String): List<RecipeDto> {
        val result = apiHelper.handleHttpRequest { spoonacularApiService.searchFoodsByIds(ids) }
        return when (result) {
            is Resource.Success -> result.data
            is Resource.Error -> throw Throwable(result.error)
        }
    }
}