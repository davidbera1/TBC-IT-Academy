package com.example.learnandroid.domain.use_case

import javax.inject.Inject

class ValidatePasswordUseCase @Inject constructor() {
    operator fun invoke(password: String): Boolean {
        return password.isNotEmpty() && password.length >= 8
    }
}