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

    override suspend fun getRandomRecipes(number: Int): Resource<RandomRecipesDto> {
        return apiHelper.handleHttpRequest { spoonacularApiService.getRandomRecipes(number) }
    }
}