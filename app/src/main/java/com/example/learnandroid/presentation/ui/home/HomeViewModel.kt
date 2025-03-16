package com.example.learnandroid.presentation.ui.home

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.learnandroid.domain.use_case.GetUsersUseCase
import com.example.learnandroid.presentation.base.BaseViewModel
import com.example.learnandroid.presentation.mapper.toPresentation
import com.example.learnandroid.presentation.model.UserUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getUsersUseCase: GetUsersUseCase
) : BaseViewModel<HomeState, HomeIntent, HomeEffect>(HomeState()) {

    override suspend fun handleIntent(intent: HomeIntent) {
        when (intent) {
            HomeIntent.RefreshUsers -> refreshUsers()
            HomeIntent.ProfileButtonClicked -> emitEffect(HomeEffect.NavigateToProfile)
        }
    }

    private fun refreshUsers() {
        val users: Flow<PagingData<UserUi>> = getUsersUseCase()
            .map { pagingData ->
                pagingData.map { user -> user.toPresentation() }
            }.cachedIn(viewModelScope)

        updateState { copy(pagingData = users) }
    }
}