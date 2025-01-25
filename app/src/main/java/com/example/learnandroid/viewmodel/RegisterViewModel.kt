package com.example.learnandroid.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.learnandroid.client.RetrofitClient
import com.example.learnandroid.model.AuthDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {

    private val _registerResult = MutableStateFlow<Boolean?>(null)
    val registerResult: StateFlow<Boolean?> = _registerResult

    fun register(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = RetrofitClient.authService.register(AuthDto(email = email, password = password))
                _registerResult.value = response.isSuccessful
            } catch (e: Throwable) {
                _registerResult.value = false
            }
        }
    }
}