package com.example.learnandroid.data.model

import kotlinx.serialization.Serializable

@Serializable
data class RecipeDto(
    val id: Int,
    val image: String,
    val title: String,
    val readyInMinutes: Int,
    val servings: Int,
    val sourceUrl: String,
    val vegetarian: Boolean,
    val vegan: Boolean,
    val glutenFree: Boolean,
    val dairyFree: Boolean,
    val cheap: Boolean,
    val veryPopular: Boolean,
    val healthScore: Double,
    val pricePerServing: Double,
    val extendedIngredients: List<IngredientDto>,
    val summary: String,
    val cuisines: List<String>,
    val dishTypes: List<String>,
    val diets: List<String>,
    val instructions: String
)