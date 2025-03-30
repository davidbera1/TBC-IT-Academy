package com.example.learnandroid.domain.use_case

import javax.inject.Inject

class ValidateAccountUseCase @Inject constructor() {
    operator fun invoke(accountNumber: String): Boolean {
        return accountNumber.length == 9 || accountNumber.length == 11 || accountNumber.length == 23
    }
}