package com.example.learnandroid.presentation.mapper

import com.example.learnandroid.domain.model.User
import com.example.learnandroid.presentation.model.UserUi

fun User.toPresentation(): UserUi {
    return UserUi(
        id = id,
        email = email,
        fullName = "$firstName $lastName",
        avatar = avatar
    )
}