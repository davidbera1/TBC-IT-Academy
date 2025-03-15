package com.example.learnandroid.data.repository

import com.example.learnandroid.data.mapper.toDomain
import com.example.learnandroid.data.model.AuthDto
import com.example.learnandroid.data.remote.RegisterService
import com.example.learnandroid.data.remote.common.ApiHelper
import com.example.learnandroid.domain.common.Resource
import com.example.learnandroid.domain.common.mapResource
import com.example.learnandroid.domain.model.RegisterResponse
import com.example.learnandroid.domain.repository.RegisterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RegisterRepositoryImpl @Inject constructor(
    private val registerService: RegisterService,
    private val apiHelper: ApiHelper
) : RegisterRepository {

    override fun register(email: String, password: String): Flow<Resource<RegisterResponse>> {
        return apiHelper.handleHttpRequest {
            registerService.register(AuthDto(email = email, password = password))
        }.mapResource {
            it.toDomain()
        }
    }
}
