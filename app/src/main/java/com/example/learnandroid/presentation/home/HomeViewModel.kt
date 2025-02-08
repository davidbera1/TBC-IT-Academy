package com.example.learnandroid.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.learnandroid.data.remote.RetrofitClient
import com.example.learnandroid.data.remote.UsersPagingSource

class HomeViewModel : ViewModel() {

    val usersPager = Pager(
        config = PagingConfig(
            pageSize = 6,
            prefetchDistance = 1
        ),
        pagingSourceFactory = { UsersPagingSource(RetrofitClient.userService) }
    ).flow.cachedIn(viewModelScope)
}