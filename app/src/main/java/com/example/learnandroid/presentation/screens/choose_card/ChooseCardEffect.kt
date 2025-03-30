package com.example.learnandroid.presentation.screens.choose_card

import com.example.learnandroid.presentation.model.CardUi

sealed class ChooseCardEffect {
    data class ShowToast(val message: String) : ChooseCardEffect()
    data object NavigateBack : ChooseCardEffect()
    data class NavigateBackWithCardDetails(val card: CardUi) : ChooseCardEffect()
}