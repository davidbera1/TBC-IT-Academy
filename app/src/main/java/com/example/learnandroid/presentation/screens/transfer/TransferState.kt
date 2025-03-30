package com.example.learnandroid.presentation.screens.transfer

import com.example.learnandroid.presentation.model.CardUi

data class TransferState(
    val fromCard: CardUi? = null,
    val toCard: CardUi? = null,
    val sell: Double = 0.0,
    val buy: Double = 0.0,
    val description: String = "",
    val currency: Double = 1.0
)