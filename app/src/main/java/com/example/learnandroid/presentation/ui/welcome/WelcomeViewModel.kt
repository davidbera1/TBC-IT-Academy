package com.example.learnandroid.presentation.ui.welcome

import androidx.lifecycle.ViewModel
import com.example.learnandroid.domain.model.UserSession
import com.example.learnandroid.domain.use_case.ReadUserSessionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor(
    private val readUserSessionUseCase: ReadUserSessionUseCase
) : ViewModel() {
    fun getUserSession(): Flow<UserSession?> {
        return readUserSessionUseCase()
    }
}