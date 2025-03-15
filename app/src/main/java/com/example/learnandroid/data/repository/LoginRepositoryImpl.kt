package com.example.learnandroid.data.repository

import com.example.learnandroid.data.mapper.toDomain
import com.example.learnandroid.data.model.AuthDto
import com.example.learnandroid.data.remote.LoginService
import com.example.learnandroid.data.remote.common.ApiHelper
import com.example.learnandroid.domain.common.Resource
import com.example.learnandroid.domain.common.mapResource
import com.example.learnandroid.domain.model.LoginResponse
import com.example.learnandroid.domain.repository.LoginRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val loginService: LoginService,
    private val apiHelper: ApiHelper
) : LoginRepository {

    override fun login(email: String, password: String): Flow<Resource<LoginResponse>> {
        return apiHelper.handleHttpRequest {
            loginService.login(AuthDto(email = email, password = password))
        }.mapResource {
            it.toDomain()
        }
    }
}