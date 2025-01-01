package com.example.learnandroid

import java.util.UUID

data class MessageItem (
    val id: String = UUID.randomUUID().toString(),
    val message: String,
    val sendDate: String
)