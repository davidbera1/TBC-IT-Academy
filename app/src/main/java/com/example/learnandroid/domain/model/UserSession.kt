package com.example.learnandroid.domain.model

data class UserSession(
    val isLoggedIn: Boolean = false,
    val email: String,
    val token: String
)