package com.example.learnandroid.data.repository

import com.example.learnandroid.data.model.PlaceDto

interface PlacesRepository {
    suspend fun getPlaces() : List<PlaceDto>
}