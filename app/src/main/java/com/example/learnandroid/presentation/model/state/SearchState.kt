package com.example.learnandroid.presentation.model.state

import com.example.learnandroid.presentation.model.Search

data class SearchState(
    val loader: Boolean = false,
    val search: Search? = null,
    val error: String? = null
)