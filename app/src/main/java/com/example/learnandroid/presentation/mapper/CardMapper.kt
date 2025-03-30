package com.example.learnandroid.presentation.mapper

import com.example.learnandroid.domain.model.Card
import com.example.learnandroid.presentation.model.CardUi

fun Card.toPresenter(): CardUi {
    return CardUi(
        id = id,
        accountName = accountName,
        accountNumber = accountNumber,
        valuteType = valuteType.toCurrency(),
        cardType = cardType.toCardType(),
        balance = balance,
        cardLogo = cardLogo
    )
}

fun String.toCurrency(): CardUi.Currency {
    return when (this.uppercase()) {
        "GEL" -> CardUi.Currency.GEL
        "USD" -> CardUi.Currency.USD
        "EUR" -> CardUi.Currency.EUR
        else -> {
            CardUi.Currency.USD
        }
    }
}

fun String.toCardType(): CardUi.CardType {
    return when(this) {
        "MASTER_CARD" -> CardUi.CardType.MASTER_CARD
        "VISA" -> CardUi.CardType.VISA
        else -> {
            CardUi.CardType.VISA
        }
    }
}