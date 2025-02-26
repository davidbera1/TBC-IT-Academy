package com.example.learnandroid.data.model

import kotlinx.serialization.Serializable

@Serializable
data class RandomRecipesDto(
    val recipes: List<RecipeDto>
)
