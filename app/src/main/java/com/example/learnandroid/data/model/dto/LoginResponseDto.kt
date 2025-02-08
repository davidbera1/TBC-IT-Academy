package com.example.learnandroid.data.model.dto

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponseDto(
    val token: String
)
