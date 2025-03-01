package com.example.learnandroid.presentation.mapper

import com.example.learnandroid.data.model.IngredientDto
import com.example.learnandroid.data.model.RandomRecipesDto
import com.example.learnandroid.data.model.RecipeDto
import com.example.learnandroid.presentation.model.Ingredient
import com.example.learnandroid.presentation.model.RandomRecipes
import com.example.learnandroid.presentation.model.Recipe

fun RandomRecipesDto.toRandomRecipes() : RandomRecipes {
    return RandomRecipes(
        recipes = recipes.map { it.toRecipe() }
    )
}

private fun RecipeDto.toRecipe() : Recipe {
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
        extendedIngredients = extendedIngredients.map { it.toIngredient() },
        summary = summary,
        cuisines = cuisines,
        instructions = instructions
    )
}

private fun IngredientDto.toIngredient() : Ingredient {
    return Ingredient(
        name = name,
        amount = amount,
        unit = measures.metric.unitLong
    )
}
