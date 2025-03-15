package com.example.learnandroid.domain.repository

import com.example.learnandroid.domain.common.Resource
import com.example.learnandroid.domain.model.LoginResponse
import kotlinx.coroutines.flow.Flow

interface LoginRepository {
    fun login(email: String, password: String): Flow<Resource<LoginResponse>>
}