package com.example.learnandroid.data.mapper

import com.example.learnandroid.data.model.CategoryDto
import com.example.learnandroid.domain.model.Category

fun CategoryDto.toDomain(): Category {
    return Category(
        id = id,
        name = name,
        nameDe = nameDe,
        createdAt = createdAt,
        bglNumber = bglNumber,
        bglVariant = bglVariant,
        orderId = orderId,
        main = main,
        children = children.map { it.toDomain() }
    )
}