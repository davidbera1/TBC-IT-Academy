package com.example.learnandroid.presentation.screens.home

sealed class HomeEvent {
    data object AddImageButtonClicked : HomeEvent()
}