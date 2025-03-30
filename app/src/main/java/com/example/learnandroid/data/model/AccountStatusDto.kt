package com.example.learnandroid.data.model

import kotlinx.serialization.Serializable

@Serializable
data class AccountStatusDto(
    val status: String
)