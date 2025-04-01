package com.example.learnandroid.domain.use_case

import com.example.learnandroid.domain.common.Resource
import com.example.learnandroid.domain.model.RegisterResponse
import com.example.learnandroid.domain.repository.RegisterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val registerRepository: RegisterRepository,
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase
) {
    operator fun invoke(
        email: String,
        password: String,
        repeatPassword: String
    ): Flow<Resource<RegisterResponse>> {

        if (email.isEmpty() || password.isEmpty() || repeatPassword.isEmpty()) {
            return flowOf(Resource.Error("Please fill all fields"))
        }

        if (password != repeatPassword) {
            return flowOf(Resource.Error("Passwords do not match"))
        }

        if (!validateEmailUseCase(email)) {
            return flowOf(Resource.Error("Invalid email"))
        }

        if (!validatePasswordUseCase(password)) {
            return flowOf(Resource.Error("Invalid password"))
        }

        return registerRepository.register(email.lowercase(), password)
    }
}