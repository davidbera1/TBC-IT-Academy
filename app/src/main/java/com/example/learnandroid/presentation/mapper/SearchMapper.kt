package com.example.learnandroid.presentation.mapper

import com.example.learnandroid.data.model.SearchDto
import com.example.learnandroid.presentation.model.Search

fun SearchDto.toSearch() : Search{
    return Search(
        results = searchResults.first().results.map { it.toFood() }
    )
}

fun SearchDto.FoodDto.toFood() : Search.Food {
    return Search.Food(
        id = id,
        name = name,
        image = image,
        summary = summary
    )
}