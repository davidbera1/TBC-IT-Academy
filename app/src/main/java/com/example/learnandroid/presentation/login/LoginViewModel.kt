package com.example.learnandroid.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.learnandroid.data.model.dataclass.LoginResult
import com.example.learnandroid.data.model.dto.AuthDto
import com.example.learnandroid.data.remote.RetrofitClient
import com.example.learnandroid.data.remote.common.ApiHelper
import com.example.learnandroid.data.remote.common.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val _loginResult = MutableSharedFlow<LoginResult>()
    val loginResult: SharedFlow<LoginResult> = _loginResult

    fun login(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _loginResult.emit(LoginResult(loader = true))
            val response = ApiHelper.handleHttpRequest {
                RetrofitClient.authService.login(AuthDto(email = email, password = password))
            }
            when (response) {
                is Resource.Success -> {
                    _loginResult.emit(
                        LoginResult(
                            token = response.data.token,
                            loginResult = true,
                            loader = false
                        )
                    )
                }

                is Resource.Error -> {
                    _loginResult.emit(
                        LoginResult(
                            errorMessage = response.error,
                            loginResult = false,
                            loader = false
                        )
                    )
                }
            }
        }
    }
}
