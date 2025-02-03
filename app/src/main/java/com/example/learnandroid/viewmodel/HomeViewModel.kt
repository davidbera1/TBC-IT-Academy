package com.example.learnandroid.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.learnandroid.room.UserRepository
import com.example.learnandroid.room.entities.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : ViewModel() {

    private val userRepository = UserRepository(application.applicationContext)

    val users: Flow<List<User>> = userRepository.getUsersFromDB()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun refreshUsers() {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.value = true
            userRepository.fetchUsersFromApi()
            _isLoading.value = false
        }
    }
}