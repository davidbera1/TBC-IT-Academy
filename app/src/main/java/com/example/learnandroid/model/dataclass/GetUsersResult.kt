package com.example.learnandroid.model.dataclass

import com.example.learnandroid.model.dto.UsersDto

data class GetUsersResult(
    val loader: Boolean? = null,
    val users: UsersDto? = null,
    val errorMessage: String? = null
)
