package com.example.learnandroid.data.model

data class UserSession(
    val isLoggedIn: Boolean = false,
    val email: String? = null,
    val token: String? = null
)