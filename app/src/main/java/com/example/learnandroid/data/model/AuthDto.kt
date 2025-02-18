package com.example.learnandroid.data.model

import kotlinx.serialization.Serializable

@Serializable
data class AuthDto(
    val email: String,
    val password: String
)
