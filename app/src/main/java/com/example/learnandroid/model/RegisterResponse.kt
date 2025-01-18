package com.example.learnandroid.model

import kotlinx.serialization.Serializable

@Serializable
data class RegisterResponse(
    val id: Int,
    val token: String
)
