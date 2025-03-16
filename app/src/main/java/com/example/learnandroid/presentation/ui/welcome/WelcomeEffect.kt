package com.example.learnandroid.presentation.ui.welcome

sealed class WelcomeEffect {
    data object NavigateToRegister : WelcomeEffect()
    data object NavigateToLogin : WelcomeEffect()
    data object NavigateToHome : WelcomeEffect()
}