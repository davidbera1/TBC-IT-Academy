package com.example.learnandroid.presentation.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.learnandroid.data.repository.SpoonacularRepositoryImpl
import com.example.learnandroid.presentation.mapper.toRandomRecipes
import com.example.learnandroid.presentation.model.RandomRecipesState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val spoonacularRepository: SpoonacularRepositoryImpl
) : ViewModel() {

    private val _randomRecipes = MutableStateFlow(RandomRecipesState())
    val randomRecipes: StateFlow<RandomRecipesState> = _randomRecipes

    init {
        getRandomRecipes()
    }

    // get 10 random recipes when home page starts
    private fun getRandomRecipes() {
        viewModelScope.launch(Dispatchers.IO) {
            _randomRecipes.value = RandomRecipesState(loader = true)
            try {
                val result = spoonacularRepository.getRandomRecipes(10).toRandomRecipes()
                _randomRecipes.value = RandomRecipesState(randomRecipes = result, loader = false)
            } catch (e: Throwable) {
                _randomRecipes.value = RandomRecipesState(error = e.message, loader = false)
            }
        }
    }
}