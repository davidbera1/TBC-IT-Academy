package com.example.learnandroid.presentation.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.learnandroid.data.repository.FirebaseRepository
import com.example.learnandroid.data.model.AuthResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val firebaseRepository: FirebaseRepository) : ViewModel() {

    private val _registerState = MutableSharedFlow<AuthResult>(0)
    val registerState: SharedFlow<AuthResult> = _registerState

    fun register(email: String, password: String) {
        viewModelScope.launch {
            _registerState.emit(AuthResult(loader = true))

            try {
                val result = firebaseRepository.register(email, password)
                _registerState.emit(AuthResult(result = result, loader = false))
            } catch (e: Throwable) {
                _registerState.emit(AuthResult(result = Result.failure(e), loader = false))
            }
        }
    }
}