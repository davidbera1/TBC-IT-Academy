package com.example.learnandroid.presentation.mapper

import com.example.learnandroid.data.model.PlaceDto
import com.example.learnandroid.presentation.model.Place

fun PlaceDto.toPlace(): Place {
    return Place(
        latitude = lat,
        longitude = lan,
        placeTitle = title,
        address = address
    )
}