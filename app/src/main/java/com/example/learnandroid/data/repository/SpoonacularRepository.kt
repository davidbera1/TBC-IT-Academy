package com.example.learnandroid.data.repository

import com.example.learnandroid.data.model.RandomRecipesDto
import com.example.learnandroid.data.remote.common.Resource

interface SpoonacularRepository {
    suspend fun getRandomRecipes(number: Int): RandomRecipesDto
}