package com.example.learnandroid.data.model.dto

import kotlinx.serialization.Serializable

@Serializable
data class RegisterResponseDto(
    val id: Int,
    val token: String
)
