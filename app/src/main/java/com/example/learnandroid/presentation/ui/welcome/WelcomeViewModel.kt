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
) : BaseViewModel<WelcomeState, WelcomeIntent, WelcomeEffect>(WelcomeState()) {

    override suspend fun handleIntent(intent: WelcomeIntent) {
        when (intent) {
            WelcomeIntent.GetUserSession -> getUserSession()
            WelcomeIntent.LoginButtonClicked -> emitEffect(WelcomeEffect.NavigateToLogin)
            WelcomeIntent.RegisterButtonClicked -> emitEffect(WelcomeEffect.NavigateToRegister)
        }
    }

    private fun getUserSession() {
        viewModelScope.launch {
            updateState { copy(isLoading = true) }
            val userSession = readUserSessionUseCase().first()
            if (userSession.isLoggedIn) {
                emitEffect(WelcomeEffect.NavigateToHome)
            }
            updateState { copy(isLoading = false) }
        }
    }
}