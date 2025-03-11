package com.example.learnandroid.data.repository

import com.example.learnandroid.data.mapper.toLoginResponse
import com.example.learnandroid.data.model.AuthDto
import com.example.learnandroid.data.remote.LoginService
import com.example.learnandroid.data.remote.common.ApiHelper
import com.example.learnandroid.domain.common.Resource
import com.example.learnandroid.domain.model.LoginResponse
import com.example.learnandroid.domain.repository.LoginRepository
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val loginService: LoginService,
    private val apiHelper: ApiHelper
) : LoginRepository {

    override suspend fun login(email: String, password: String): Resource<LoginResponse> {

        val response = apiHelper.handleHttpRequest {
            loginService.login(AuthDto(email = email, password = password))
        }

        return when (response) {
            is Resource.Success -> {
                Resource.Success(response.data.toLoginResponse())
            }

            is Resource.Error -> {
                Resource.Error(response.error)
            }
        }
    }
}