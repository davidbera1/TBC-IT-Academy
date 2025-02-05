package com.example.learnandroid.presentation.security

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SecurityViewModel : ViewModel() {

    val correctPassword = "0934"

    private val _enteredPassword = MutableStateFlow("")
    val enteredPassword: StateFlow<String> = _enteredPassword

    fun insertPasswordDigit(digit: Char) {
        _enteredPassword.value += digit
    }

    fun removePasswordLastDigit() {
        if (_enteredPassword.value.isNotEmpty()) {
            _enteredPassword.value = _enteredPassword.value.dropLast(1)
        }
    }

    fun checkPassword(password: String): Boolean {
        _enteredPassword.value = ""
        return password == correctPassword
    }
}