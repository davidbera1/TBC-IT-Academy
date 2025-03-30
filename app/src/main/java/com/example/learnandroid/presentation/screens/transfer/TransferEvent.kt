package com.example.learnandroid.presentation.screens.transfer

import com.example.learnandroid.presentation.model.CardUi

sealed class TransferEvent {
    data object ChooseCardClicked : TransferEvent()
    data object ChooseAnAccountClicked : TransferEvent()
    data object GetCurrency : TransferEvent()
    data class CardSelected(val card: CardUi?) : TransferEvent()
    data class ToCardSelected(val card: CardUi?) : TransferEvent()
    data class UpdateSellInput(val sell: Double) : TransferEvent()
    data class UpdateBuyInput(val buy: Double) : TransferEvent()
    data class UpdateDescriptionInput(val description: String) : TransferEvent()
}