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
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getUsersUseCase: GetUsersUseCase
) : BaseViewModel<HomeState, HomeEvent, HomeEffect>(HomeState()) {

    init {
        refreshUsers()
    }

    override fun onEvent(event: HomeEvent) {
        viewModelScope.launch {
            when (event) {
                is HomeEvent.ProfileButtonClicked -> emitEffect(HomeEffect.NavigateToProfile)
            }
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


// region HomeState
data class HomeState(
    val isLoading: Boolean = false,
    val pagingData: Flow<PagingData<UserUi>> = emptyFlow()
)
// endregion

// region HomeEvent
sealed class HomeEvent {
    data object ProfileButtonClicked : HomeEvent()
}
// endregion

// region HomeEffect
sealed class HomeEffect {
    data object NavigateToProfile : HomeEffect()
}
// endregion