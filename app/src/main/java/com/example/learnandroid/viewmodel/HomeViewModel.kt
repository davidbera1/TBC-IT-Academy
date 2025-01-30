package com.example.learnandroid.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.learnandroid.datastore.UserManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val userManager: UserManager) : ViewModel() {

    val userFlow = userManager.getUser()

    fun updateUser(firstName: String, lastName: String, email: String) {
        viewModelScope.launch {
            userManager.updateUser(firstName, lastName, email)
        }
    }
}
