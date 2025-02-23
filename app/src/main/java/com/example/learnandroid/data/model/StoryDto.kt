package com.example.learnandroid.data.model

import kotlinx.serialization.Serializable

@Serializable
data class StoryDto(
    val id: Int,
    val cover: String,
    val title: String
)
