package com.example.learnandroid.data.model

import kotlinx.serialization.Serializable

@Serializable
data class PlaceDto(
    val lat: Double,
    val lan: Double,
    val title: String,
    val address: String
)