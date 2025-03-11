package com.example.learnandroid.domain.repository

import com.example.learnandroid.domain.common.Resource
import com.example.learnandroid.domain.model.LoginResponse

interface LoginRepository {
    suspend fun login(email: String, password: String): Resource<LoginResponse>
}