package com.example.learnandroid.data.model

import kotlinx.serialization.Serializable

@Serializable
data class RecipeDto(
    val id: Int,
    val image: String? = null,
    val title: String? = null,
    val readyInMinutes: Int? = null,
    val servings: Int? = null,
    val sourceUrl: String? = null,
    val vegetarian: Boolean? = null,
    val vegan: Boolean? = null,
    val glutenFree: Boolean? = null,
    val dairyFree: Boolean? = null,
    val cheap: Boolean? = null,
    val veryPopular: Boolean? = null,
    val healthScore: Double? = null,
    val pricePerServing: Double? = null,
    val extendedIngredients: List<IngredientDto>? = null,
    val summary: String? = null,
    val cuisines: List<String>? = null,
    val instructions: String? = null
)