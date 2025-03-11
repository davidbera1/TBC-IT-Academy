package com.example.learnandroid.domain.use_case

import com.example.learnandroid.domain.common.Resource
import com.example.learnandroid.domain.model.RegisterResponse
import com.example.learnandroid.domain.repository.RegisterRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val registerRepository: RegisterRepository,
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase
) {
    suspend operator fun invoke(
        email: String,
        password: String,
        repeatPassword: String
    ): Resource<RegisterResponse> {

        if (email.isEmpty() || password.isEmpty() || repeatPassword.isEmpty()) {
            return Resource.Error("Please fill all fields")
        }

        if (password != repeatPassword) {
            return Resource.Error("Passwords do not match")
        }

        if (!validateEmailUseCase(email)) {
            return Resource.Error("Invalid email")
        }

        if (!validatePasswordUseCase(password)) {
            return Resource.Error("Invalid password")
        }

        return registerRepository.register(email, password)
    }
}