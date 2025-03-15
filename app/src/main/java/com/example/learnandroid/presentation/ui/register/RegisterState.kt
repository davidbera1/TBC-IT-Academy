package com.example.learnandroid.presentation.ui.register

data class RegisterState(
    val isLoading: Boolean = false,
    val email: String = "",
    val password: String = "",
    val repeatPassword: String = ""
)