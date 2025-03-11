package com.example.learnandroid.presentation.ui.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.learnandroid.domain.common.Resource
import com.example.learnandroid.domain.use_case.RegisterUseCase
import com.example.learnandroid.presentation.model.RegisterResultUiActions
import com.example.learnandroid.presentation.model.RegisterState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase
) : ViewModel() {

    private val _registerState = MutableSharedFlow<RegisterState>()
    val registerState: SharedFlow<RegisterState> = _registerState

    fun register(email: String, password: String, repeatPassword: String) {
        viewModelScope.launch {
            _registerState.emit(
                RegisterState(
                    loader = true,
                    action = RegisterResultUiActions.DISABLE_LOGIN_BUTTON
                )
            )

            when (val result = registerUseCase(email, password, repeatPassword)) {
                is Resource.Success -> {
                    _registerState.emit(
                        RegisterState(
                            registerResult = true,
                            action = RegisterResultUiActions.ENABLE_LOGIN_BUTTON,
                            loader = false
                        )
                    )
                }

                is Resource.Error -> {
                    _registerState.emit(
                        RegisterState(
                            registerResult = false,
                            errorMessage = result.error,
                            action = RegisterResultUiActions.ENABLE_LOGIN_BUTTON,
                            loader = false
                        )
                    )
                }
            }
        }
    }
}