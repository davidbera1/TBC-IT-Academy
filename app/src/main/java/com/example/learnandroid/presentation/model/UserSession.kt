package com.example.learnandroid.presentation.model

data class UserSession(
    val isLoggedIn: Boolean = false,
    val email: String? = null,
    val token: String? = null
)