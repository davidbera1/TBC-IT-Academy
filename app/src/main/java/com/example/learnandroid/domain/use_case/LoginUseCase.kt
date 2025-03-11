package com.example.learnandroid.domain.use_case

import com.example.learnandroid.domain.common.Resource
import com.example.learnandroid.domain.model.LoginResponse
import com.example.learnandroid.domain.repository.LoginRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val loginRepository: LoginRepository,
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase
) {
    suspend operator fun invoke(email: String, password: String): Resource<LoginResponse> {
        if (email.isEmpty() || password.isEmpty()) {
            return Resource.Error("Please fill all fields")
        }

        if (!validateEmailUseCase(email)) {
            return Resource.Error("Invalid email")
        }

        if (!validatePasswordUseCase(password)) {
            return Resource.Error("Invalid password")
        }

        return loginRepository.login(email, password)
    }
}