package com.example.learnandroid.presentation.welcome

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.learnandroid.data.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuthException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor(private val authRepository: AuthRepository) : ViewModel() {

    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn

    init {
        checkUserSession()
    }

    private fun checkUserSession() {
        viewModelScope.launch {
            val currentUser = authRepository.getUserSession()

            if (currentUser != null) {
                try {
                    currentUser.reload().await()
                    _isLoggedIn.value = true
                } catch (e: FirebaseAuthException) {
                    authRepository.clearUserSession()
                    _isLoggedIn.value = false
                }
            } else {
                _isLoggedIn.value = false
            }
        }
    }
}