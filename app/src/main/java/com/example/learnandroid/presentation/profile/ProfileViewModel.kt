package com.example.learnandroid.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.learnandroid.data.local.datastore.DataStoreManager
import com.example.learnandroid.data.repository.FirebaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val firebaseRepository: FirebaseRepository,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    init {
        getEmail()
    }

    private fun getEmail() {
        viewModelScope.launch {
            val email = dataStoreManager.getEmail().first()
            _email.value = email
        }
    }

    fun logout() {
        firebaseRepository.logout()
    }
}