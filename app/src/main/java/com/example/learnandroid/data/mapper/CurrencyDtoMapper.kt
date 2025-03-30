package com.example.learnandroid.data.mapper

import com.example.learnandroid.data.model.CurrencyDto
import com.example.learnandroid.domain.model.Currency

fun CurrencyDto.toDomain(): Currency {
    return Currency(course = course)
}