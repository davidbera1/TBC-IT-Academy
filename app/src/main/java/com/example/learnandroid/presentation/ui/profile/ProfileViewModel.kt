package com.example.learnandroid.presentation.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.learnandroid.domain.model.UserSession
import com.example.learnandroid.domain.use_case.ClearUserSessionUseCase
import com.example.learnandroid.domain.use_case.ReadUserSessionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val readUserSessionUseCase: ReadUserSessionUseCase,
    private val clearUserSessionUseCase: ClearUserSessionUseCase
) : ViewModel() {

    fun getUserSession(): Flow<UserSession?> {
        return readUserSessionUseCase()
    }

    fun clearUserSession() {
        viewModelScope.launch {
            clearUserSessionUseCase()
        }
    }
}