package com.example.learnandroid.presentation.ui.register

sealed class RegisterIntent {
    data class SendUpdatedEmail(val email: String) : RegisterIntent()
    data class SendUpdatedPassword(val password: String) : RegisterIntent()
    data class SendUpdatedRepeatPassword(val repeatPassword: String) : RegisterIntent()
    data object RegisterButtonClicked : RegisterIntent()
    data object BackButtonClicked : RegisterIntent()
}