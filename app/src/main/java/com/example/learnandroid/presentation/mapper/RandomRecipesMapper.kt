package com.example.learnandroid.presentation.mapper

import com.example.learnandroid.data.model.RandomRecipesDto
import com.example.learnandroid.presentation.model.RandomRecipes

fun RandomRecipesDto.toRandomRecipes() : RandomRecipes {
    return RandomRecipes(
        recipes = recipes.map { it.toRecipe() }
    )
}
