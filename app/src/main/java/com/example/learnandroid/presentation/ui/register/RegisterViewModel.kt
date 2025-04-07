package com.example.learnandroid.presentation.ui.register

import androidx.lifecycle.viewModelScope
import com.example.learnandroid.domain.common.Resource
import com.example.learnandroid.domain.use_case.RegisterUseCase
import com.example.learnandroid.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.example.learnandroid.presentation.ui.register.RegisterEvent.*
import kotlinx.coroutines.launch

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase
) : BaseViewModel<RegisterState, RegisterEvent, RegisterEffect>(RegisterState()) {

    override fun onEvent(event: RegisterEvent) {
        when (event) {
            is SendUpdatedEmail -> updateState { copy(email = event.email) }

            is SendUpdatedPassword -> updateState { copy(password = event.password) }

            is SendUpdatedRepeatPassword -> updateState { copy(repeatPassword = event.repeatPassword) }

            is RegisterButtonClicked -> register()

            is BackButtonClicked -> emitEffect(RegisterEffect.NavigateToWelcome)
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
                        emitEffect(RegisterEffect.NavigateToLogin)
                    }

                    is Resource.Loader -> updateState { copy(isLoading = result.isLoading) }

                    is Resource.Error -> {
                        updateState { copy(isLoading = false) }
                        emitEffect(RegisterEffect.ShowSnackbar(result.errorMessage))
                    }
                }
            }
        }
    }
}


// region RegisterState
data class RegisterState(
    val isLoading: Boolean = false,
    val email: String = "",
    val password: String = "",
    val repeatPassword: String = ""
)
// endregion

// region RegisterEvent
sealed class RegisterEvent {
    data class SendUpdatedEmail(val email: String) : RegisterEvent()
    data class SendUpdatedPassword(val password: String) : RegisterEvent()
    data class SendUpdatedRepeatPassword(val repeatPassword: String) : RegisterEvent()
    data object RegisterButtonClicked : RegisterEvent()
    data object BackButtonClicked : RegisterEvent()
}
// endregion

// region RegisterEffect
sealed class RegisterEffect {
    data class ShowSnackbar(val message: String) : RegisterEffect()
    data object NavigateToLogin : RegisterEffect()
    data object NavigateToWelcome : RegisterEffect()
}
// endregion