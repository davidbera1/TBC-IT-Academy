package com.example.learnandroid.presentation.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.learnandroid.domain.common.Resource
import com.example.learnandroid.domain.model.UserSession
import com.example.learnandroid.domain.use_case.LoginUseCase
import com.example.learnandroid.domain.use_case.SaveUserSessionUseCase
import com.example.learnandroid.presentation.model.LoginResultUiActions
import com.example.learnandroid.presentation.model.LoginState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val saveUserSessionUseCase: SaveUserSessionUseCase,
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _loginState = MutableSharedFlow<LoginState>()
    val loginState: SharedFlow<LoginState> = _loginState

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _loginState.emit(
                LoginState(
                    loader = true,
                    action = LoginResultUiActions.DISABLE_LOGIN_BUTTON
                )
            )

            when (val result = loginUseCase(email, password)) {
                is Resource.Success -> {
                    _loginState.emit(
                        LoginState(
                            loginResult = true,
                            action = LoginResultUiActions.ENABLE_LOGIN_BUTTON,
                            loader = false
                        )
                    )
                }

                is Resource.Error -> {
                    _loginState.emit(
                        LoginState(
                            loginResult = false,
                            errorMessage = result.error,
                            action = LoginResultUiActions.ENABLE_LOGIN_BUTTON,
                            loader = false
                        )
                    )
                }
            }
        }
    }

    fun saveUserSession(userSession: UserSession) {
        viewModelScope.launch {
            saveUserSessionUseCase(userSession)
        }
    }
}
