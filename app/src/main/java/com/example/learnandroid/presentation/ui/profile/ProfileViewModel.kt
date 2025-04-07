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
) : BaseViewModel<ProfileState, ProfileEvent, ProfileEffect>(ProfileState()) {

    init {
        getUserSession()
    }

    override fun onEvent(event: ProfileEvent) {
        when (event) {
            is ProfileEvent.LogoutButtonClicked -> {
                viewModelScope.launch {
                    clearUserSession()
                    emitEffect(ProfileEffect.NavigateToWelcome)
                }
            }
        }
    }

    private fun getUserSession() {
        viewModelScope.launch {
            val userSession = readUserSessionUseCase().first()
            updateState { copy(email = userSession.email) }
        }
    }

    private suspend fun clearUserSession() {
        clearUserSessionUseCase()
    }
}


// region ProfileState
data class ProfileState(
    val isLoading: Boolean = false,
    val email: String = "",
)
// endregion

// region ProfileEvent
sealed class ProfileEvent {
    data object LogoutButtonClicked : ProfileEvent()
}
// endregion

// region ProfileEffect
sealed class ProfileEffect {
    data class ShowSnackbar(val message: String) : ProfileEffect()
    data object NavigateToWelcome : ProfileEffect()
}
// endregion