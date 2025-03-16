package com.example.learnandroid.presentation.ui.home

sealed class HomeEffect {
    data class ShowToast(val message: String) : HomeEffect()
    data object NavigateToProfile : HomeEffect()
}