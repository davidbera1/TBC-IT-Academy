package com.example.learnandroid.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.learnandroid.client.RetrofitClient
import com.example.learnandroid.model.dataclass.RegisterResult
import com.example.learnandroid.model.dto.AuthDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {

    private val _registerResult = MutableSharedFlow<RegisterResult>()
    val registerResult: SharedFlow<RegisterResult> = _registerResult

    fun register(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _registerResult.emit(RegisterResult(loader = true))
            try {
                val response =
                    RetrofitClient.authService.register(AuthDto(email = email, password = password))
                if (response.isSuccessful) {
                    _registerResult.emit(
                        RegisterResult(
                            id = response.body()?.id,
                            token = response.body()?.token,
                            registerResult = true,
                            loader = false
                        )
                    )
                } else {
                    _registerResult.emit(
                        RegisterResult(
                            errorMessage = response.errorBody()?.string(),
                            registerResult = false,
                            loader = false
                        )
                    )
                }
            } catch (e: Throwable) {
                _registerResult.emit(
                    RegisterResult(
                        errorMessage = e.message,
                        registerResult = false,
                        loader = false
                    )
                )
            }
        }
    }
}