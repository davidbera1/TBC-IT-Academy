package com.example.learnandroid.presentation.screens.transfer

sealed class TransferEffect {
    data object NavigateToChooseCard : TransferEffect()
    data object NavigateToChooseAccount : TransferEffect()
    data class UpdateSellInput(val sell: Double) : TransferEffect()
    data class UpdateBuyInput(val buy: Double) : TransferEffect()
}