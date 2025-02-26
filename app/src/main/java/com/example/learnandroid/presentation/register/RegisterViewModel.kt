package com.example.learnandroid.presentation.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.learnandroid.data.repository.FirebaseRepositoryImpl
import com.example.learnandroid.data.model.AuthResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val firebaseRepositoryImpl: FirebaseRepositoryImpl) : ViewModel() {

    private val _registerState = MutableSharedFlow<AuthResult>(0)
    val registerState: SharedFlow<AuthResult> = _registerState

    fun register(email: String, password: String) {
        viewModelScope.launch {
            _registerState.emit(AuthResult(loader = true))

            try {
                val result = firebaseRepositoryImpl.register(email, password)
                _registerState.emit(AuthResult(result = result, loader = false))
            } catch (e: Throwable) {
                _registerState.emit(AuthResult(result = Result.failure(e), loader = false))
            }
        }
    }
}