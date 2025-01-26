package com.example.learnandroid.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.learnandroid.client.RetrofitClient
import com.example.learnandroid.model.dataclass.LoginResult
import com.example.learnandroid.model.dto.AuthDto
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
            try {
                val response = RetrofitClient.authService.login(AuthDto(email = email, password = password))

                if (response.isSuccessful) {
                    _loginResult.emit(
                        LoginResult(
                            token = response.body()?.token,
                            loginResult = true,
                            loader = false
                        )
                    )
                } else {
                    _loginResult.emit(
                        LoginResult(
                            errorMessage = response.errorBody()?.string(),
                            loginResult = false,
                            loader = false
                        )
                    )
                }
            } catch (e: Throwable) {
                _loginResult.emit(
                    LoginResult(
                        errorMessage = e.message,
                        loginResult = false,
                        loader = false
                    )
                )
            }
        }
    }
}
