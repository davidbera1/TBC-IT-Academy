package com.example.learnandroid.presentation.ui.profile

sealed class ProfileIntent {
    data object LogoutButtonClicked : ProfileIntent()
    data object ReadUserSession : ProfileIntent()
}