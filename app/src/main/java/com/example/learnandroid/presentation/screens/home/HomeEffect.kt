package com.example.learnandroid.presentation.screens.home

sealed class HomeEffect {
    data object NavigateToImagePicker : HomeEffect()
    data class ShowToast(val text: String) : HomeEffect()
}