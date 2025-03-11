package com.example.learnandroid.presentation.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.learnandroid.data.local.datastore.UserSessionManager
import com.example.learnandroid.presentation.model.UserSession
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userSessionManager: UserSessionManager
) : ViewModel() {

    fun getUserSession(): Flow<UserSession> {
        return userSessionManager.getUserSession()
    }

    fun clearUserSession() {
        viewModelScope.launch {
            userSessionManager.clearUserSession()
        }
    }
}