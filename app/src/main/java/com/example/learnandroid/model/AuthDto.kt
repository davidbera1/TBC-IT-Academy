package com.example.learnandroid.model

import kotlinx.serialization.Serializable

@Serializable
data class AuthDto(
    val email: String,
    val password: String
)
