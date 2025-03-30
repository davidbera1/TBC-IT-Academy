package com.example.learnandroid.domain.repository

import com.example.learnandroid.domain.model.Currency

interface CurrencyRepository {
    suspend fun getCurrency(): Currency
}