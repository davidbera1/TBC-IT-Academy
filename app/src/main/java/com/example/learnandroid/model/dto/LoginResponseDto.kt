package com.example.learnandroid.model.dto

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponseDto(
    val token: String
)
