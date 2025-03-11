package com.example.learnandroid.data.mapper

import com.example.learnandroid.data.model.RegisterResponseDto
import com.example.learnandroid.domain.model.RegisterResponse

fun RegisterResponseDto.toRegisterResponse(): RegisterResponse {
    return RegisterResponse(
        id = id,
        token = token
    )
}