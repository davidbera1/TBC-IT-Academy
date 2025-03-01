package com.example.learnandroid.presentation.mapper

import com.example.learnandroid.data.model.RecipeDto
import com.example.learnandroid.presentation.model.Recipe

fun RecipeDto.toRecipe() : Recipe {
    return Recipe(
        id = id,
        image = image,
        title = title,
        readyInMinutes = readyInMinutes,
        servings = servings,
        sourceUrl = sourceUrl,
        vegetarian = vegetarian,
        vegan = vegan,
        glutenFree = glutenFree,
        dairyFree = dairyFree,
        cheap = cheap,
        veryPopular = veryPopular,
        healthScore = healthScore,
        pricePerServing = pricePerServing,
        extendedIngredients = extendedIngredients?.map { it.toIngredient() },
        summary = summary,
        cuisines = cuisines,
        instructions = instructions
    )
}
