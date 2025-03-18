package com.example.learnandroid.presentation.mapper

import com.example.learnandroid.domain.model.Category
import com.example.learnandroid.presentation.model.CategoryUi

fun Category.toPresentation(): CategoryUi {
    return CategoryUi(
        id = id,
        name = name,
        children = children.map { it.toPresentation() }
    )
}