package com.example.learnandroid.presentation.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.learnandroid.data.local.room.AppDatabase
import com.example.learnandroid.data.local.room.entity.UserEntity
import com.example.learnandroid.data.remote.UserRemoteMediator
import com.example.learnandroid.data.remote.UserService
import com.example.learnandroid.domain.model.User
import com.example.learnandroid.domain.use_case.GetUsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getUsersUseCase: GetUsersUseCase
) : ViewModel() {

    @OptIn(ExperimentalPagingApi::class)
    val users: Flow<PagingData<User>> = getUsersUseCase().cachedIn(viewModelScope)
}