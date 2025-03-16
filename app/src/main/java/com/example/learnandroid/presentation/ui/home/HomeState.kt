package com.example.learnandroid.presentation.ui.home

import androidx.paging.PagingData
import com.example.learnandroid.presentation.model.UserUi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class HomeState(
    val isLoading: Boolean = false,
    val pagingData: Flow<PagingData<UserUi>> = emptyFlow()
)