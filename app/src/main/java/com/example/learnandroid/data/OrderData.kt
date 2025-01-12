package com.example.learnandroid.data

import com.example.learnandroid.model.OrderItem

object OrderData {
    private var orders: MutableList<OrderItem> = mutableListOf()

    fun addOrders(order: MutableList<OrderItem>) {
        orders.addAll(order)
    }

    fun getOrders() : MutableList<OrderItem> {
        return orders
    }
}
