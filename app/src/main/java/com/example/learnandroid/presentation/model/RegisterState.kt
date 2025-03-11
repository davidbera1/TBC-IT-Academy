package com.example.learnandroid.presentation.model

data class RegisterState(
    val loader: Boolean? = null,
    val registerResult: Boolean? = null,
    val errorMessage: String? = null,
    val data: RegisterResponseUi? = null,
    val action: RegisterResultUiActions
) {
    data class RegisterResponseUi(
        val id: Int,
        val token: String
    )
}