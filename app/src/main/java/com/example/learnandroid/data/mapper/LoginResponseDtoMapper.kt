package com.example.learnandroid.data.mapper

import com.example.learnandroid.data.model.LoginResponseDto
import com.example.learnandroid.domain.model.LoginResponse

fun LoginResponseDto.toLoginResponse(): LoginResponse {
    return LoginResponse(
        token = token
    )
}