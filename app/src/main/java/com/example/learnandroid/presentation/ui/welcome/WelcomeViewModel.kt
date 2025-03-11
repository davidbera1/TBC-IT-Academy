package com.example.learnandroid.presentation.ui.welcome

import androidx.lifecycle.ViewModel
import com.example.learnandroid.data.local.datastore.UserSessionManager
import com.example.learnandroid.presentation.model.UserSession
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor(
    private val userSessionManager: UserSessionManager
) : ViewModel() {
    fun getUserSession(): Flow<UserSession> {
        return userSessionManager.getUserSession()
    }
}