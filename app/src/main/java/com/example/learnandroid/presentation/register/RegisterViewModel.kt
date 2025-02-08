package com.example.learnandroid.presentation.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.learnandroid.data.remote.RetrofitClient
import com.example.learnandroid.data.model.dataclass.RegisterResult
import com.example.learnandroid.data.model.dto.AuthDto
import com.example.learnandroid.data.remote.common.ApiHelper
import com.example.learnandroid.data.remote.common.Resource
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
            val response = ApiHelper.handleHttpRequest {
                RetrofitClient.authService.register(AuthDto(email = email, password = password))
            }
            when (response) {
                is Resource.Success -> {
                    _registerResult.emit(
                        RegisterResult(
                            id = response.data.id,
                            token = response.data.token,
                            registerResult = true,
                            loader = false
                        )
                    )
                }
                is Resource.Error -> {
                    _registerResult.emit(
                        RegisterResult(
                            errorMessage = response.error,
                            registerResult = false,
                            loader = false
                        )
                    )
                }
            }
        }
    }
}