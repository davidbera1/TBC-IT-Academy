package com.example.learnandroid.data.repository

import com.example.learnandroid.data.mapper.toDomain
import com.example.learnandroid.data.remote.CurrencyService
import com.example.learnandroid.domain.model.Currency
import com.example.learnandroid.domain.repository.CurrencyRepository
import javax.inject.Inject

class CurrencyRepositoryImpl @Inject constructor(
    private val currencyService: CurrencyService
) : CurrencyRepository {
    override suspend fun getCurrency(): Currency {
        return currencyService.getCurrency().toDomain()
    }
}