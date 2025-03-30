package com.example.learnandroid.data.mapper

import com.example.learnandroid.data.model.CardDto
import com.example.learnandroid.domain.model.Card

fun CardDto.toDomain(): Card {
    return Card(
        id = id,
        accountName = accountName,
        accountNumber = accountNumber,
        valuteType = valuteType,
        cardType = cardType,
        balance = balance,
        cardLogo = cardLogo
    )
}