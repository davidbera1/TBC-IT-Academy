package com.example.learnandroid.domain.use_case

import com.example.learnandroid.domain.repository.CardRepository
import javax.inject.Inject

class GetCardsUseCase @Inject constructor(
    private val cardRepository: CardRepository
) {
    suspend operator fun invoke() = cardRepository.getCards()
}