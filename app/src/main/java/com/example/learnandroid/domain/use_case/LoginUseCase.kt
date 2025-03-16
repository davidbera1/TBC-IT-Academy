package com.example.learnandroid.domain.use_case

import com.example.learnandroid.domain.common.Resource
import com.example.learnandroid.domain.model.LoginResponse
import com.example.learnandroid.domain.model.UserSession
import com.example.learnandroid.domain.repository.LoginRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.transform
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val loginRepository: LoginRepository,
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
    private val saveUserSessionUseCase: SaveUserSessionUseCase,
) {
    operator fun invoke(
        email: String,
        password: String,
        rememberMe: Boolean
    ): Flow<Resource<LoginResponse>> {

        if (email.isEmpty() || password.isEmpty()) {
            return flowOf(Resource.Error("Please fill all fields"))
        }

        if (!validateEmailUseCase(email)) {
            return flowOf(Resource.Error("Invalid email"))
        }

        if (!validatePasswordUseCase(password)) {
            return flowOf(Resource.Error("Invalid password"))
        }

        return loginRepository.login(email, password).transform { result ->
            when (result) {
                is Resource.Success -> {
                    saveUserSession(email, result.data.token, rememberMe)
                    emit(Resource.Success(result.data))
                }

                else -> {
                    emit(result)
                }
            }
        }
    }

    private suspend fun saveUserSession(email: String, token: String, rememberMe: Boolean) {
        var userSession = UserSession(
            email = email,
            token = token,
            isLoggedIn = true
        )
        if (rememberMe) {
            saveUserSessionUseCase(userSession)
        } else {
            userSession = userSession.copy(isLoggedIn = false)
            saveUserSessionUseCase(userSession)
        }
    }
}