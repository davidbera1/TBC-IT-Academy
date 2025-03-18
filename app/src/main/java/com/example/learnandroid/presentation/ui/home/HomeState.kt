package com.example.learnandroid.presentation.ui.home

import com.example.learnandroid.presentation.model.CategoryUi

data class HomeState(
    val isLoading: Boolean = false,
    val categories: List<Pair<CategoryUi, Int>> = emptyList(),
    val searchQuery: String = ""
)