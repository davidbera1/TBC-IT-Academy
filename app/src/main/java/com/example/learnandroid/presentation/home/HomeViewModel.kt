package com.example.learnandroid.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.learnandroid.data.repository.PlacesRepositoryImpl
import com.example.learnandroid.presentation.mapper.toPlace
import com.example.learnandroid.presentation.model.PlaceState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val placesRepository: PlacesRepositoryImpl
) : ViewModel() {

    private val _places = MutableStateFlow(PlaceState())
    val places: StateFlow<PlaceState> = _places

    init {
        getPlaces()
    }

    private fun getPlaces() {
        viewModelScope.launch(Dispatchers.IO) {
            _places.value = PlaceState(isLoading = true)
            try {
                val places = placesRepository.getPlaces().map { it.toPlace() }
                _places.value = PlaceState(places = places, isLoading = false)
            } catch (e: Throwable) {
                _places.value = PlaceState(error = e.message ?: "Error", isLoading = false)
            }
        }
    }
}