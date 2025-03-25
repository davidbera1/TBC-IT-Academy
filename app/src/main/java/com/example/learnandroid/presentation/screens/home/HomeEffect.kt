package com.example.learnandroid.presentation.screens.home

sealed class HomeEffect {
    data object NavigateToImagePicker : HomeEffect()
}