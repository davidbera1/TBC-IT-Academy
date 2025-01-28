package com.example.learnandroid.model.dataclass

import com.example.learnandroid.model.dto.UsersDto

data class LoginResult(
    val loader: Boolean? = null,
    val token: String? = null,
    val errorMessage: String? = null,
    val loginResult: Boolean? = null
)

data class RegisterResult(
    val id: Int? = null,
    val loader: Boolean? = null,
    val token: String? = null,
    val errorMessage: String? = null,
    val registerResult: Boolean? = null
)

data class GetUsersResult(
    val loader: Boolean? = null,
    val users: UsersDto? = null,
    val errorMessage: String? = null
)

