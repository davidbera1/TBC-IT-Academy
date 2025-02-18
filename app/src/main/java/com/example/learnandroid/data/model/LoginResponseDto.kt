package com.example.learnandroid.data.model

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponseDto(
    val token: String
)
