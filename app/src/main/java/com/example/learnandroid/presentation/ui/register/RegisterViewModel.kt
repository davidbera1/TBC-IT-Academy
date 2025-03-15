package com.example.learnandroid.presentation.ui.register

import androidx.lifecycle.viewModelScope
import com.example.learnandroid.domain.common.Resource
import com.example.learnandroid.domain.use_case.RegisterUseCase
import com.example.learnandroid.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.example.learnandroid.presentation.ui.register.RegisterIntent.*
import kotlinx.coroutines.launch

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase
) : BaseViewModel<RegisterState, RegisterIntent, RegisterEffect>(RegisterState()) {

    override suspend fun handleIntent(intent: RegisterIntent) {
        when (intent) {
            is SendUpdatedEmail -> updateState { copy(email = intent.email) }

            is SendUpdatedPassword -> updateState { copy(password = intent.password) }

            is SendUpdatedRepeatPassword -> updateState { copy(repeatPassword = intent.repeatPassword) }

            is RegisterButtonClicked -> register()

            BackButtonClicked -> emitEffect(RegisterEffect.NavigateToLogin)
        }
    }

    private fun register() {
        val email = state.value.email
        val password = state.value.password
        val repeatPassword = state.value.repeatPassword
        viewModelScope.launch {
            registerUseCase(email, password, repeatPassword).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        updateState { copy(isLoading = false) }
                        emitEffect(RegisterEffect.ShowToast("Registration successful"))
                        emitEffect(RegisterEffect.NavigateToLogin)
                    }

                    is Resource.Loader -> updateState { copy(isLoading = result.isLoading) }

                    is Resource.Error -> {
                        updateState { copy(isLoading = false) }
                        emitEffect(RegisterEffect.ShowToast(result.errorMessage))
                    }
                }
            }
        }
    }
}