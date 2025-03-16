package com.example.learnandroid.presentation.ui.welcome

sealed class WelcomeIntent {
    data object RegisterButtonClicked : WelcomeIntent()
    data object LoginButtonClicked : WelcomeIntent()
    data object GetUserSession : WelcomeIntent()
}