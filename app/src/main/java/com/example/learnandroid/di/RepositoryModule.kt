package com.example.learnandroid.di

import com.example.learnandroid.data.repository.CardRepositoryImpl
import com.example.learnandroid.data.repository.CurrencyRepositoryImpl
import com.example.learnandroid.domain.repository.CardRepository
import com.example.learnandroid.domain.repository.CurrencyRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindCardRepository(cardRepository: CardRepositoryImpl): CardRepository

    @Binds
    @Singleton
    abstract fun bindCurrencyRepository(currencyRepository: CurrencyRepositoryImpl): CurrencyRepository

}