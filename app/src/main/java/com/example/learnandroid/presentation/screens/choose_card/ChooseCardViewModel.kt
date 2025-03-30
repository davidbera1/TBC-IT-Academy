package com.example.learnandroid.presentation.screens.choose_card

import androidx.lifecycle.viewModelScope
import com.example.learnandroid.domain.common.Resource
import com.example.learnandroid.domain.use_case.GetCardsUseCase
import com.example.learnandroid.presentation.base.BaseViewModel
import com.example.learnandroid.presentation.mapper.toPresenter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChooseCardViewModel @Inject constructor(
    private val getCardsUseCase: GetCardsUseCase
) : BaseViewModel<ChooseCardState, ChooseCardEvent, ChooseCardEffect>(ChooseCardState()) {

    init {
        viewModelScope.launch {
            getCardsUseCase().collect {
                when (it) {
                    is Resource.Error -> emitEffect(ChooseCardEffect.ShowToast(it.errorMessage))
                    is Resource.Loader -> updateState { copy(isLoading = it.isLoading) }
                    is Resource.Success -> updateState {
                        copy(cards = it.data.map { card ->
                            card.toPresenter()
                        })
                    }
                }
            }
        }
    }

    override fun onEvent(event: ChooseCardEvent) {
        viewModelScope.launch {
            when (event) {
                is ChooseCardEvent.OnBackClicked -> emitEffect(ChooseCardEffect.NavigateBack)
                is ChooseCardEvent.OnCardClicked -> {
                    emitEffect(ChooseCardEffect.NavigateBackWithCardDetails(event.card))
                }
            }
        }
    }
}