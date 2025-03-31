package com.example.learnandroid.presentation.ui.welcome

sealed class WelcomeEvent {
    data object RegisterButtonClicked : WelcomeEvent()
    data object LoginButtonClicked : WelcomeEvent()
}