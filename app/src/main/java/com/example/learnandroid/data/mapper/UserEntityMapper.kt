package com.example.learnandroid.data.mapper

import com.example.learnandroid.data.local.room.entity.UserEntity
import com.example.learnandroid.domain.model.User

fun UserEntity.toUser(): User {
    return User(
        id = id,
        email = email,
        firstName = firstName,
        lastName = lastName,
        avatar = avatar
    )
}