package com.example.learnandroid.presentation.ui.welcome

import androidx.lifecycle.viewModelScope
import com.example.learnandroid.domain.use_case.ReadUserSessionUseCase
import com.example.learnandroid.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor(
    private val readUserSessionUseCase: ReadUserSessionUseCase
) : BaseViewModel<WelcomeState, WelcomeEvent, WelcomeEffect>(WelcomeState()) {

    init {
        getUserSession()
    }

    override fun onEvent(event: WelcomeEvent) {
        viewModelScope.launch {
            when (event) {
                WelcomeEvent.LoginButtonClicked -> emitEffect(WelcomeEffect.NavigateToLogin)
                WelcomeEvent.RegisterButtonClicked -> emitEffect(WelcomeEffect.NavigateToRegister)
            }
        }
    }

    private fun getUserSession() {
        viewModelScope.launch {
            updateState { copy(isLoading = true) }
            val userSession = readUserSessionUseCase().first()
            if (userSession.isLoggedIn) {
                updateState { copy(isLoading = false) }
                emitEffect(WelcomeEffect.NavigateToHome)
            } else {
                updateState { copy(isLoading = false) }
                return@launch
            }
        }
    }
}


// region WelcomeState
data class WelcomeState(
    val isLoading: Boolean = false
)
// endregion

// region WelcomeEvent
sealed class WelcomeEvent {
    data object RegisterButtonClicked : WelcomeEvent()
    data object LoginButtonClicked : WelcomeEvent()
}
// endregion

// region WelcomeEffect
sealed class WelcomeEffect {
    data object NavigateToRegister : WelcomeEffect()
    data object NavigateToLogin : WelcomeEffect()
    data object NavigateToHome : WelcomeEffect()
}
// endregion