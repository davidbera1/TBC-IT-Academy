package com.example.learnandroid.presentation.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.learnandroid.data.model.RandomRecipesDto
import com.example.learnandroid.data.remote.common.Resource
import com.example.learnandroid.data.repository.SpoonacularRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val spoonacularRepository: SpoonacularRepositoryImpl
) : ViewModel() {

    private val _randomRecipes = MutableStateFlow<List<RandomRecipesDto>>(emptyList())
    val randomRecipes: StateFlow<List<RandomRecipesDto>> = _randomRecipes

    fun getRandomRecipes(amount: Int) {
        viewModelScope.launch {
            val response = spoonacularRepository.getRandomRecipes(amount)
            when (response) {
                is Resource.Success -> {
                    _randomRecipes.value = listOf(response.data)
                    Log.d("!!!", "success")
                }

                is Resource.Error -> {
                    Log.d("!!!", "Error in viewmodel ${response.error}")
                }
            }
        }
    }
}