package com.example.learnandroid.data.repository

import com.example.learnandroid.data.mapper.toRegisterResponse
import com.example.learnandroid.data.model.AuthDto
import com.example.learnandroid.data.remote.RegisterService
import com.example.learnandroid.data.remote.common.ApiHelper
import com.example.learnandroid.domain.common.Resource
import com.example.learnandroid.domain.model.RegisterResponse
import com.example.learnandroid.domain.repository.RegisterRepository
import javax.inject.Inject

class RegisterRepositoryImpl @Inject constructor(
    private val registerService: RegisterService,
    private val apiHelper: ApiHelper
) : RegisterRepository {

    override suspend fun register(email: String, password: String): Resource<RegisterResponse> {

        val response = apiHelper.handleHttpRequest {
            registerService.register(AuthDto(email = email, password = password))
        }

        return when (response) {
            is Resource.Success -> {
                Resource.Success(response.data.toRegisterResponse())
            }

            is Resource.Error -> {
                Resource.Error(response.error)
            }
        }
    }
}
