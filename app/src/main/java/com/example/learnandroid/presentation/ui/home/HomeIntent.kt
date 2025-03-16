package com.example.learnandroid.presentation.ui.home

sealed class HomeIntent {
    data object RefreshUsers : HomeIntent()
    data object ProfileButtonClicked : HomeIntent()
}