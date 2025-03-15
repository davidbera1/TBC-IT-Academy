package com.example.learnandroid.presentation.ui.login

data class LoginState(
    val isLoading: Boolean = false,
    val email: String = "",
    val password: String = "",
    val isRememberMeChecked: Boolean = false
)
