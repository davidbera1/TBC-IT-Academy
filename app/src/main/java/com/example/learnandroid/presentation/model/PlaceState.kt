package com.example.learnandroid.presentation.model

data class PlaceState(
    val places: List<Place> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)