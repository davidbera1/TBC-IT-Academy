package com.example.learnandroid.presentation.welcome

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.learnandroid.data.local.datastore.DataStoreManager
import com.example.learnandroid.data.repository.FirebaseRepositoryImpl
import com.google.firebase.auth.FirebaseAuthException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor(
    private val firebaseRepositoryImpl: FirebaseRepositoryImpl,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn

    private val _language = MutableStateFlow("")
    val language: StateFlow<String> = _language

    init {
        getLanguage()
        checkUserSession()
    }

    private fun getLanguage() {
        viewModelScope.launch {
            val language = dataStoreManager.getLanguage().first()
            _language.value = language
        }
    }

    fun saveLanguage(language: String) {
        _language.value = language
        viewModelScope.launch {
            dataStoreManager.saveLanguage(language)
        }
    }

    private fun checkUserSession() {
        viewModelScope.launch {
            val currentUser = firebaseRepositoryImpl.getUserSession()

            if (currentUser != null) {
                try {
                    currentUser.reload().await()
                    _isLoggedIn.value = true
                } catch (e: FirebaseAuthException) {
                    firebaseRepositoryImpl.clearUserSession()
                    _isLoggedIn.value = false
                }
            } else {
                _isLoggedIn.value = false
            }
        }
    }
}