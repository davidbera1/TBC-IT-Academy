package com.example.learnandroid.data.repository

import com.example.learnandroid.data.model.RandomRecipesDto
import com.example.learnandroid.data.model.RecipeDto
import com.example.learnandroid.data.model.SearchDto

interface SpoonacularRepository {
    suspend fun getRandomRecipes(number: Int): RandomRecipesDto

    suspend fun searchFoodByName(query: String): SearchDto

    suspend fun searchFoodById(id: Int): RecipeDto
}