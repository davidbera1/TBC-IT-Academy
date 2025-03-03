package com.example.learnandroid.presentation.model.state

import com.example.learnandroid.presentation.model.Recipe

data class FavoriteRecipesState(
    val loader: Boolean = false,
    val favoriteRecipes: List<Recipe> = emptyList(),
    val error: String? = null
)