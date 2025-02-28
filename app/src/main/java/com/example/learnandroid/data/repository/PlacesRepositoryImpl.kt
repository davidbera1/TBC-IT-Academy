package com.example.learnandroid.data.repository

import com.example.learnandroid.data.model.PlaceDto
import com.example.learnandroid.data.remote.PlacesApi
import com.example.learnandroid.data.remote.common.ApiHelper
import com.example.learnandroid.data.remote.common.Resource
import javax.inject.Inject

class PlacesRepositoryImpl @Inject constructor(
    private val api: PlacesApi,
    private val apiHelper: ApiHelper
) : PlacesRepository {
    override suspend fun getPlaces(): List<PlaceDto> {
        val result = apiHelper.handleHttpRequest { api.getPlaces() }

        return when (result) {
            is Resource.Success -> result.data
            is Resource.Error -> throw Throwable(result.error)
        }
    }
}