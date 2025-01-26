package com.example.learnandroid.model.dataclass

data class LoginResult(
    val loader: Boolean? = null,
    val token: String? = null,
    val errorMessage: String? = null,
    val loginResult: Boolean? = null
)
