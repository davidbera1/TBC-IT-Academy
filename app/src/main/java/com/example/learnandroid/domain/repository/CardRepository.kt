package com.example.learnandroid.domain.repository

import com.example.learnandroid.domain.common.Resource
import com.example.learnandroid.domain.model.Card
import kotlinx.coroutines.flow.Flow

interface CardRepository {
    suspend fun getCards(): Flow<Resource<List<Card>>>
}