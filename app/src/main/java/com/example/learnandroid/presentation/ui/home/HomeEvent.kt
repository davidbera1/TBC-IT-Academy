package com.example.learnandroid.presentation.ui.home

sealed class HomeEvent {
    data object GetAllCategories : HomeEvent()
    data class UpdateSearchQuery(val query: String) : HomeEvent()
}