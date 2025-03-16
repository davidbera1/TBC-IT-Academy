package com.example.learnandroid.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UsersDto(
    @SerialName("data")
    val user: List<UserDto>
) {
    @Serializable
    data class UserDto(
        val id: Int,
        val email: String,
        @SerialName("first_name")
        val firstName: String,
        @SerialName("last_name")
        val lastName: String,
        val avatar: String
    )
}