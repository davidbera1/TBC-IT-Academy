package com.example.learnandroid.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchDto(
    val searchResults: List<Results>
) {
    @Serializable
    data class Results(
        val results: List<FoodDto>
    )

    @Serializable
    data class FoodDto(
        val id: Int? = null, // some foods from spoonacular don't return IDs :(
        val name: String? = null,
        val image: String? = null,
        @SerialName("content") val summary: String? = null
    )
}