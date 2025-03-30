package com.example.learnandroid.data.repository

import com.example.learnandroid.data.mapper.toDomain
import com.example.learnandroid.data.remote.CardService
import com.example.learnandroid.data.remote.common.ApiHelper
import com.example.learnandroid.domain.common.Resource
import com.example.learnandroid.domain.common.mapResource
import com.example.learnandroid.domain.model.Card
import com.example.learnandroid.domain.repository.CardRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CardRepositoryImpl @Inject constructor(
    private val cardService: CardService,
    private val apiHelper: ApiHelper
) : CardRepository {

    override suspend fun getCards(): Flow<Resource<List<Card>>> {
        return apiHelper.handleHttpRequest { cardService.getCards() }.mapResource { cardsList ->
            cardsList.map { cardDto ->
                cardDto.toDomain()
            }
        }
    }
}