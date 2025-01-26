package com.example.learnandroid.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.learnandroid.client.RetrofitClient
import com.example.learnandroid.model.dataclass.GetUsersResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val _getUsersResult = MutableStateFlow(GetUsersResult())
    val getUsersResult: StateFlow<GetUsersResult> = _getUsersResult

    fun getUsers() {
        viewModelScope.launch(Dispatchers.IO) {
            _getUsersResult.value = _getUsersResult.value.copy(loader = true)
            try {
                val response = RetrofitClient.authService.getUsers()

                if (response.isSuccessful) {
                    _getUsersResult.value = _getUsersResult.value.copy(users = response.body())
                } else {
                    _getUsersResult.value = _getUsersResult.value.copy(errorMessage = response.errorBody().toString())
                }
            } catch (e: Throwable) {
                _getUsersResult.value = _getUsersResult.value.copy(errorMessage = e.message)
            }
            _getUsersResult.value = _getUsersResult.value.copy(loader = false)
        }
    }
}