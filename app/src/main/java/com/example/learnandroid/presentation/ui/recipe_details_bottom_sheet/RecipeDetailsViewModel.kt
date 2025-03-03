package com.example.learnandroid.presentation.ui.recipe_details_bottom_sheet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.learnandroid.data.local.datastore.FavoritesManager
import com.example.learnandroid.data.repository.SpoonacularRepositoryImpl
import com.example.learnandroid.presentation.mapper.toRecipe
import com.example.learnandroid.presentation.model.state.SearchByIdState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeDetailsViewModel @Inject constructor(
    private val spoonacularRepository: SpoonacularRepositoryImpl,
    private val favoritesManager: FavoritesManager
) : ViewModel() {

    private val _searchByIdResult = MutableStateFlow(SearchByIdState())
    val searchResult: StateFlow<SearchByIdState> = _searchByIdResult

    private val _favoriteIdList = MutableStateFlow<List<Int>>(emptyList())
    val favoriteIdList: StateFlow<List<Int>> = _favoriteIdList

    init {
        getFavoriteIds()
    }

    private fun getFavoriteIds() {
        viewModelScope.launch {
            val favoriteIds = favoritesManager.getFavoriteIdList().first()
            _favoriteIdList.value = favoriteIds
        }
    }

    fun saveFavoriteId(id: Int) {
        viewModelScope.launch {
            favoritesManager.saveFavoriteId(id)
            getFavoriteIds()
        }
    }

    fun removeFavoriteId(id: Int) {
        viewModelScope.launch {
            favoritesManager.removeFavoriteId(id)
            getFavoriteIds()
        }
    }

    fun searchFoodById(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _searchByIdResult.value = SearchByIdState(loader = true)
            try {
                val result = spoonacularRepository.searchFoodById(id).toRecipe()
                _searchByIdResult.value = SearchByIdState(searchByIdResult = result, loader = false)
            } catch (e: Throwable) {
                _searchByIdResult.value = SearchByIdState(error = e.message, loader = false)
            }
        }
    }
}