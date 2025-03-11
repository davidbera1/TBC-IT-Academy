package com.example.learnandroid.presentation.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.learnandroid.data.local.room.AppDatabase
import com.example.learnandroid.data.local.room.entity.User
import com.example.learnandroid.data.remote.UserRemoteMediator
import com.example.learnandroid.data.remote.UserService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val database: AppDatabase,
    private val userService: UserService
) : ViewModel() {

    @OptIn(ExperimentalPagingApi::class)
    val usersPager: Flow<PagingData<User>> = Pager(
        config = PagingConfig(
            pageSize = 6,
            initialLoadSize = 6,
            prefetchDistance = 0
        ),
        remoteMediator = UserRemoteMediator(userService, database.userDao()),
        pagingSourceFactory = { database.userDao().getUsers() }
    ).flow.cachedIn(viewModelScope)
}