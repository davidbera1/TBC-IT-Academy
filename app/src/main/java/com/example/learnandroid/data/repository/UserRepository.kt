package com.example.learnandroid.data.repository

import com.example.learnandroid.data.model.AuthDto
import com.example.learnandroid.data.model.LoginResult
import com.example.learnandroid.data.model.RegisterResult
import com.example.learnandroid.data.remote.AuthService
import com.example.learnandroid.data.remote.common.ApiHelper
import com.example.learnandroid.data.remote.common.Resource
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val authService: AuthService
) {

    suspend fun login(email: String, password: String): LoginResult {

        val loginResult = LoginResult(loader = true)

        val response = ApiHelper.handleHttpRequest {
            authService.login(AuthDto(email = email, password = password))
        }

        return when (response) {
            is Resource.Success -> {
                loginResult.copy(
                    token = response.data.token,
                    loginResult = true,
                    loader = false
                )
            }
            is Resource.Error -> {
                loginResult.copy(
                    errorMessage = response.error,
                    loginResult = false,
                    loader = false
                )
            }
        }
    }

    suspend fun register(email: String, password: String): RegisterResult {

        val registerResult = RegisterResult(loader = true)

        val response = ApiHelper.handleHttpRequest {
            authService.register(AuthDto(email = email, password = password))
        }

        return when (response) {
            is Resource.Success -> {
                registerResult.copy(
                    id = response.data.id,
                    token = response.data.token,
                    registerResult = true,
                    loader = false
                )
            }
            is Resource.Error -> {
                registerResult.copy(
                    errorMessage = response.error,
                    registerResult = false,
                    loader = false
                )
            }
        }
    }
}
