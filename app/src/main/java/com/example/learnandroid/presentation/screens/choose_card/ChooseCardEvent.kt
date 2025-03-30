package com.example.learnandroid.presentation.screens.choose_card

import com.example.learnandroid.presentation.model.CardUi

sealed class ChooseCardEvent {
    data object OnBackClicked : ChooseCardEvent()
    data class OnCardClicked(val card: CardUi) : ChooseCardEvent()
}