package com.example.learnandroid.domain.use_case

import javax.inject.Inject

class ValidateEmailUseCase @Inject constructor() {
    operator fun invoke(email: String): Boolean {
        return email.isNotEmpty() && email.length >= 10 && email.contains("@") && email.contains(".")
    }
}