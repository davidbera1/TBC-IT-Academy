package com.example.learnandroid.presentation.ui.login

sealed class LoginIntent {
    data class SendUpdatedEmail(val email: String) : LoginIntent()
    data class SendUpdatedPassword(val password: String) : LoginIntent()
    data object LoginButtonClicked : LoginIntent()
    data class RememberMeChecked(val isChecked: Boolean) : LoginIntent()
}