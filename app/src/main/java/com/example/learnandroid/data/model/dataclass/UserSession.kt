package com.example.learnandroid.data.model.dataclass

data class UserSession(
    val isLoggedIn: Boolean = false,
    val email: String? = null,
    val token: String? = null
)