package com.example.learnandroid.presentation.ui.register

sealed class RegisterEvent {
    data class SendUpdatedEmail(val email: String) : RegisterEvent()
    data class SendUpdatedPassword(val password: String) : RegisterEvent()
    data class SendUpdatedRepeatPassword(val repeatPassword: String) : RegisterEvent()
    data object RegisterButtonClicked : RegisterEvent()
    data object BackButtonClicked : RegisterEvent()
}