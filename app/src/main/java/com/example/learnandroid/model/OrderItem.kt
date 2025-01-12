package com.example.learnandroid.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.UUID

@Parcelize
data class OrderItem(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val color: String,
    val status: String,
    val quantity: Int,
    val price: Double,
    var feedback: String = "",
    val image: Int
) : Parcelable
