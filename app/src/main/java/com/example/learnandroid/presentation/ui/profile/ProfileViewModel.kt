package com.example.learnandroid.presentation.ui.profile

import androidx.lifecycle.viewModelScope
import com.example.learnandroid.domain.use_case.ClearUserSessionUseCase
import com.example.learnandroid.domain.use_case.ReadUserSessionUseCase
import com.example.learnandroid.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val readUserSessionUseCase: ReadUserSessionUseCase,
    private val clearUserSessionUseCase: ClearUserSessionUseCase
) : BaseViewModel<ProfileState, ProfileIntent, ProfileEffect>(ProfileState()) {

    override suspend fun handleIntent(intent: ProfileIntent) {
        when (intent) {
            ProfileIntent.LogoutButtonClicked -> {
                emitEffect(ProfileEffect.ShowToast("Logged out"))
                emitEffect(ProfileEffect.NavigateToLogin)
                clearUserSession()
            }

            ProfileIntent.ReadUserSession -> getUserSession()
        }
    }

    private fun getUserSession() {
        viewModelScope.launch {
            val userSession = readUserSessionUseCase().first()
            updateState { copy(email = userSession.email) }
        }
    }

    private fun clearUserSession() {
        viewModelScope.launch {
            clearUserSessionUseCase()
        }
    }
}