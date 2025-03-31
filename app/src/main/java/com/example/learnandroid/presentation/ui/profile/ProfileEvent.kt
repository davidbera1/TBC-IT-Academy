package com.example.learnandroid.presentation.ui.profile

sealed class ProfileEvent {
    data object LogoutButtonClicked : ProfileEvent()
    data object ReadUserSession : ProfileEvent()
}