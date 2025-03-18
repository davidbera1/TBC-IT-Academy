package com.example.learnandroid.presentation.model

data class CategoryUi(
    val id: String,
    val name: String,
    val children: List<CategoryUi> = emptyList()
)