package com.example.learnandroid.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.learnandroid.data.model.AuthResult
import com.example.learnandroid.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val authRepository: AuthRepository) : ViewModel() {

    private val _loginState = MutableSharedFlow<AuthResult>(0)
    val loginState: SharedFlow<AuthResult> = _loginState

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _loginState.emit(AuthResult(loader = true))

            try {
                val result = authRepository.login(email, password)
                _loginState.emit(AuthResult(result = result, loader = false))
            } catch (e: Throwable) {
                _loginState.emit(AuthResult(result = Result.failure(e), loader = false))
            }
        }
    }
}