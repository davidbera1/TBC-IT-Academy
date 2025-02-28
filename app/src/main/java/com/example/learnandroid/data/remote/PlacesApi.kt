package com.example.learnandroid.data.remote

import com.example.learnandroid.data.model.PlaceDto
import retrofit2.Response
import retrofit2.http.GET

interface PlacesApi {
    @GET("c4c64996-4ed9-4cbc-8986-43c4990d495a")
    suspend fun getPlaces(): Response<List<PlaceDto>>
}