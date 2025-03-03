package com.example.learnandroid.presentation.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.learnandroid.data.repository.SpoonacularRepositoryImpl
import com.example.learnandroid.presentation.mapper.toRandomRecipes
import com.example.learnandroid.presentation.mapper.toSearch
import com.example.learnandroid.presentation.model.state.RandomRecipesState
import com.example.learnandroid.presentation.model.state.SearchState
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

    private val _searchResult = MutableStateFlow(SearchState())
    val searchResult: StateFlow<SearchState> = _searchResult

    fun getRandomRecipes(amount: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _randomRecipes.value = RandomRecipesState(loader = true)
            try {
                val result = spoonacularRepository.getRandomRecipes(amount).toRandomRecipes()
                _randomRecipes.value = RandomRecipesState(randomRecipes = result, loader = false)
            } catch (e: Throwable) {
                _randomRecipes.value = RandomRecipesState(error = e.message, loader = false)
            }
        }
    }

    fun searchFoodByName(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _searchResult.value = SearchState(loader = true)
            try {
                val result = spoonacularRepository.searchFoodByName(query).toSearch()
                if (result.results.isEmpty()) {
                    _searchResult.value = SearchState(error = "Nothing found", loader = false)
                } else {
                    _searchResult.value = SearchState(search = result, loader = false)
                }
            } catch (e: Throwable) {
                _searchResult.value = SearchState(error = e.message, loader = false)
            }
        }
    }

    fun resetSearchError() {
        _searchResult.value = _searchResult.value.copy(error = null)
    }
}