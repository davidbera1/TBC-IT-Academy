package com.example.learnandroid.presentation.model

data class RandomRecipesState(
    val loader: Boolean = false,
    val randomRecipes: RandomRecipes = RandomRecipes(emptyList()),
    val error: String? = null
)