package com.example.learnandroid.presentation.screens.choose_card

import com.example.learnandroid.presentation.model.CardUi

data class ChooseCardState(
    val isLoading: Boolean = false,
    val cards: List<CardUi> = emptyList(),
)