package com.example.learnandroid.domain.repository

import com.example.learnandroid.domain.common.Resource
import com.example.learnandroid.domain.model.RegisterResponse

interface RegisterRepository {
    suspend fun register(email: String, password: String): Resource<RegisterResponse>
}