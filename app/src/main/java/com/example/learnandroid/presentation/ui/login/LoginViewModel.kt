package com.example.learnandroid.presentation.ui.login

import androidx.lifecycle.viewModelScope
import com.example.learnandroid.domain.common.Resource
import com.example.learnandroid.domain.use_case.LoginUseCase
import com.example.learnandroid.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.learnandroid.presentation.ui.login.LoginEvent.*

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : BaseViewModel<LoginState, LoginEvent, LoginEffect>(LoginState()) {

    override fun onEvent(event: LoginEvent) {
        viewModelScope.launch {
            when (event) {
                is SendUpdatedEmail -> updateState { copy(email = event.email) }

                is SendUpdatedPassword -> updateState { copy(password = event.password) }

                is LoginButtonClicked -> login()

                is RememberMeChecked -> updateState { copy(isRememberMeChecked = event.isChecked) }
            }
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
