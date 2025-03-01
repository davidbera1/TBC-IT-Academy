package com.example.learnandroid.presentation.model.state

import com.example.learnandroid.presentation.model.RandomRecipes

data class RandomRecipesState(
    val loader: Boolean = false,
    val randomRecipes: RandomRecipes = RandomRecipes(emptyList()),
    val error: String? = null
)