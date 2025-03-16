package com.example.learnandroid.presentation.ui.register

sealed class RegisterEffect {
    data class ShowToast(val message: String) : RegisterEffect()
    data object NavigateToLogin : RegisterEffect()
    data object NavigateToHome : RegisterEffect()
}