package com.example.learnandroid.presentation.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.learnandroid.data.model.RegisterResult
import com.example.learnandroid.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _registerResult = MutableSharedFlow<RegisterResult>()
    val registerResult: SharedFlow<RegisterResult> = _registerResult

    fun register(email: String, password: String) {
        viewModelScope.launch {
            val result = userRepository.register(email, password)
            _registerResult.emit(result)
        }
    }
}