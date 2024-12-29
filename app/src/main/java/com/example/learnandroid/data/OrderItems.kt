package com.example.learnandroid.data

data class OrderItems(
    val orderId: Int,
    val trackingNumber: String,
    val quantity: Int,
    var status: String = "pending",
    val date: Long,
    val subtotal: String
)
