package com.example.learnandroid.presentation.mapper

import com.example.learnandroid.data.model.IngredientDto
import com.example.learnandroid.presentation.model.Ingredient

fun IngredientDto.toIngredient() : Ingredient {
    return Ingredient(
        name = name,
        amount = amount,
        unit = measures.metric.unitLong
    )
}
