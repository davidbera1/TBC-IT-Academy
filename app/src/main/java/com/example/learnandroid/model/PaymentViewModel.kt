package com.example.learnandroid.model

import androidx.lifecycle.ViewModel

class PaymentViewModel : ViewModel() {

    private val cardList: MutableList<CardDetails> = mutableListOf()

    fun getCardList(): List<CardDetails> {
        return cardList
    }

    fun addCard(cardDetails: CardDetails) {
        cardList.add(cardDetails)
    }

    fun deleteCard(id: String) {
        val card = cardList.find { it.id == id }
        cardList.remove(card)
    }
}