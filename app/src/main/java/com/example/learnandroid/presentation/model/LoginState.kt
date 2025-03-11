package com.example.learnandroid.presentation.model

data class LoginState(
    val loader: Boolean? = null,
    val loginResult: Boolean? = null,
    val errorMessage: String? = null,
    val data: LoginResponseUi? = null,
    val action: LoginResultUiActions
) {
    data class LoginResponseUi(
        val token: String
    )
}