package com.example.learnandroid.presentation.model.state

import com.example.learnandroid.presentation.model.Recipe

data class SearchByIdState(
    val loader: Boolean = false,
    val searchByIdResult: Recipe? = null,
    val error: String? = null
)