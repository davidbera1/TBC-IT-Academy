package com.example.learnandroid.presentation.mapper

import com.example.learnandroid.presentation.model.Recipe
import com.example.learnandroid.presentation.model.Search

fun Search.Food.toRecipe(): Recipe {
    return Recipe(
        id = id ?: -1,
        title = name,
        image = image,
        summary = summary
    )
}