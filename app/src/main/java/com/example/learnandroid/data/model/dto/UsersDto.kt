package com.example.learnandroid.data.model.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UsersDto(
    val data: List<Data>
) {
    @Serializable
    data class Data(
        val id: Int,
        val email: String,
        @SerialName("first_name")
        val firstName: String,
        @SerialName("last_name")
        val lastName: String,
        val avatar: String
    )
}
