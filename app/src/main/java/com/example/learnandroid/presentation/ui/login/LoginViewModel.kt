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
        when (event) {
            is SendUpdatedEmail -> updateState { copy(email = event.email) }

            is SendUpdatedPassword -> updateState { copy(password = event.password) }

            is LoginButtonClicked -> login()

            is SendUpdatedRememberMe -> updateState { copy(isRememberMeChecked = event.isChecked) }
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
                        emitEffect(LoginEffect.NavigateToHome)
                    }

                    is Resource.Loader -> {
                        updateState { copy(isLoading = result.isLoading) }
                    }

                    is Resource.Error -> {
                        updateState { copy(isLoading = false) }
                        emitEffect(LoginEffect.ShowSnackbar(result.errorMessage))
                    }
                }
            }
        }
    }
}


// region LoginState
data class LoginState(
    val isLoading: Boolean = false,
    val email: String = "",
    val password: String = "",
    val isRememberMeChecked: Boolean = false
)
// endregion

// region LoginEvent
sealed class LoginEvent {
    data class SendUpdatedEmail(val email: String) : LoginEvent()
    data class SendUpdatedPassword(val password: String) : LoginEvent()
    data class SendUpdatedRememberMe(val isChecked: Boolean) : LoginEvent()
    data object LoginButtonClicked : LoginEvent()
}
// endregion

// region LoginEffect
sealed class LoginEffect {
    data class ShowSnackbar(val message: String) : LoginEffect()
    data object NavigateToHome : LoginEffect()
}
// endregion