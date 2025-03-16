package com.example.learnandroid.presentation.ui.profile

sealed class ProfileEffect {
    data class ShowToast(val message: String) : ProfileEffect()
    data object NavigateToLogin : ProfileEffect()
}