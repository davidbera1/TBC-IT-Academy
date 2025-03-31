package com.example.learnandroid.presentation.ui.home

sealed class HomeEvent {
    data object RefreshUsers : HomeEvent()
    data object ProfileButtonClicked : HomeEvent()
}