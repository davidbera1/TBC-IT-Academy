package com.example.learnandroid.presentation.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.learnandroid.data.local.datastore.EmailPreferencesManager
import com.example.learnandroid.data.local.datastore.LanguagePreferencesManager
import com.example.learnandroid.data.repository.FirebaseRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val firebaseRepositoryImpl: FirebaseRepositoryImpl,
    private val emailPreferencesManager: EmailPreferencesManager,
    private val languagePreferencesManager: LanguagePreferencesManager
) : ViewModel() {

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    private val _language = MutableStateFlow("")
    val language: StateFlow<String> = _language

    init {
        getLanguage()
        getEmail()
    }

    private fun getLanguage() {
        viewModelScope.launch {
            val language = languagePreferencesManager.getLanguage().first()
            _language.value = language
        }
    }

    fun saveLanguage(language: String) {
        _language.value = language
        viewModelScope.launch {
            languagePreferencesManager.saveLanguage(language)
        }
    }

    private fun getEmail() {
        viewModelScope.launch {
            val email = emailPreferencesManager.getEmail().first()
            _email.value = email
        }
    }

    fun logout() {
        firebaseRepositoryImpl.logout()
    }
}