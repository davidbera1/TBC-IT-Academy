package com.example.learnandroid.presentation.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CardUi(
    val id: Int = -1,
    val accountName: String? = null,
    val accountNumber: String? = null,
    val valuteType: Currency? = null,
    val cardType: CardType? = null,
    val balance: Int? = null,
    val cardLogo: String? = null
) : Parcelable {
    sealed interface Currency : Parcelable {
        @Parcelize
        data object GEL : Currency

        @Parcelize
        data object USD : Currency

        @Parcelize
        data object EUR : Currency
    }

    sealed interface CardType : Parcelable {
        @Parcelize
        data object VISA : CardType

        @Parcelize
        data object MASTER_CARD : CardType
    }
}