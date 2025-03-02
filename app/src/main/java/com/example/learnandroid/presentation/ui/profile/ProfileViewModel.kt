package com.example.learnandroid.presentation.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.learnandroid.data.local.datastore.DataStoreManager
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
    private val dataStoreManager: DataStoreManager
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

    private fun getEmail() {
        viewModelScope.launch {
            val email = dataStoreManager.getEmail().first()
            _email.value = email
        }
    }

    fun logout() {
        firebaseRepositoryImpl.logout()
    }
}