package com.example.learnandroid.presentation.ui.login

import androidx.lifecycle.viewModelScope
import com.example.learnandroid.domain.common.Resource
import com.example.learnandroid.domain.use_case.LoginUseCase
import com.example.learnandroid.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.learnandroid.presentation.ui.login.LoginIntent.*

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : BaseViewModel<LoginState, LoginIntent, LoginEffect>(LoginState()) {

    override suspend fun handleIntent(intent: LoginIntent) {
        when (intent) {
            is SendUpdatedEmail -> updateState { copy(email = intent.email) }

            is SendUpdatedPassword -> updateState { copy(password = intent.password) }

            is LoginButtonClicked -> login()

            is RememberMeChecked -> updateState { copy(isRememberMeChecked = intent.isChecked) }
        }
    }

    private fun login() {
        val email = state.value.email
        val password = state.value.password
        val rememberMe = state.value.isRememberMeChecked
        viewModelScope.launch {
            loginUseCase(email, password, rememberMe).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        updateState { copy(isLoading = false) }
                        emitEffect(LoginEffect.ShowToast("Login successful"))
                        emitEffect(LoginEffect.NavigateToHome)
                    }

                    is Resource.Loader -> {
                        updateState { copy(isLoading = result.isLoading) }
                    }

                    is Resource.Error -> {
                        updateState { copy(isLoading = false) }
                        emitEffect(LoginEffect.ShowToast(result.errorMessage))
                    }
                }
            }
        }
    }
}
