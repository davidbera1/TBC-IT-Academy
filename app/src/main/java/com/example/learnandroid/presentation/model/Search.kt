package com.example.learnandroid.presentation.model

data class Search(
    val results: List<Food>
) {
    data class Food(
        val id: Int?,
        val name: String? = null,
        val image: String? = null,
        val summary : String? = null
    )
}