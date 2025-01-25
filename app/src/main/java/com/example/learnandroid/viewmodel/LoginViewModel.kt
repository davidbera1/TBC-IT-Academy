package com.example.learnandroid.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.learnandroid.client.RetrofitClient
import com.example.learnandroid.model.AuthDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val _loginResult = MutableStateFlow<Boolean?>(null)
    val loginResult: StateFlow<Boolean?> = _loginResult

    private val _token = MutableStateFlow<String?>(null)
    val token: StateFlow<String?> = _token

    fun login(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = RetrofitClient.authService.login(AuthDto(email = email, password = password))

                if (response.isSuccessful) {
                    _loginResult.value = true
                    _token.value = response.body()?.token
                } else {
                    _loginResult.value = false
                }
            } catch (e: Throwable) {
                _loginResult.value = false
            }
        }
    }
}