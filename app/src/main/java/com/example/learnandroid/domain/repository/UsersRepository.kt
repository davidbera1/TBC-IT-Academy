package com.example.learnandroid.domain.repository

import androidx.paging.PagingData
import com.example.learnandroid.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UsersRepository {
    fun getUsers() : Flow<PagingData<User>>
}