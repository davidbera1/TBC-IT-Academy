package com.example.learnandroid.data.repository

import com.example.learnandroid.data.model.RandomRecipesDto
import com.example.learnandroid.data.remote.SpoonacularApiService
import com.example.learnandroid.data.remote.common.ApiHelper
import com.example.learnandroid.data.remote.common.Resource
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
}