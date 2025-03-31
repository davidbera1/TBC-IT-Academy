package com.example.learnandroid.presentation.ui.login

sealed class LoginEvent {
    data class SendUpdatedEmail(val email: String) : LoginEvent()
    data class SendUpdatedPassword(val password: String) : LoginEvent()
    data class SendUpdatedRememberMe(val isChecked: Boolean) : LoginEvent()
    data object LoginButtonClicked : LoginEvent()
}