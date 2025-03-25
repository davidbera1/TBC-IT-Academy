package com.example.learnandroid.presentation.screens.home

import androidx.lifecycle.viewModelScope
import com.example.learnandroid.presentation.base.BaseViewModel
import kotlinx.coroutines.launch

class HomeViewModel : BaseViewModel<HomeState, HomeEvent, HomeEffect>(HomeState()) {

    override fun onEvent(event: HomeEvent) {
        viewModelScope.launch {
            when (event) {
                is HomeEvent.AddImageButtonClicked -> emitEffect(HomeEffect.NavigateToImagePicker)
            }
        }
    }
}