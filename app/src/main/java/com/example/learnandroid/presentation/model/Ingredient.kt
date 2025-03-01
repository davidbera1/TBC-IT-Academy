package com.example.learnandroid.presentation.model

data class Ingredient(
    val name: String,
    val amount: Double? = null,
    val unit: String? = null
)