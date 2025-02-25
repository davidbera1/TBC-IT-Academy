package com.example.learnandroid.data.model

import kotlinx.serialization.Serializable

@Serializable
data class IngredientDto(
    val name: String,
    val amount: Double,
    val measure: String
)