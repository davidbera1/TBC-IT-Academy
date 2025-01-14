package com.example.learnandroid.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CardDetails (
    val id: String,
    val image: Int,
    val cardHolderName: String,
    val cardNumber: String,
    val expires: String,
    val cvv: Int,
    val cardType: CardType
) : Parcelable