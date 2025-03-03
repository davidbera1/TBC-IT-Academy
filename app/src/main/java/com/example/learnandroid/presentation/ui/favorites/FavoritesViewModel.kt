package com.example.learnandroid.presentation.ui.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.learnandroid.data.local.datastore.FavoritesManager
import com.example.learnandroid.data.repository.SpoonacularRepositoryImpl
import com.example.learnandroid.presentation.mapper.toRecipe
import com.example.learnandroid.presentation.model.state.FavoriteRecipesState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val spoonacularRepository: SpoonacularRepositoryImpl,
    private val favoritesManager: FavoritesManager
) : ViewModel() {

    private var favoriteIdList = emptyList<Int>()

    private val _favoriteRecipes = MutableStateFlow(FavoriteRecipesState())
    val favoriteRecipes: StateFlow<FavoriteRecipesState> = _favoriteRecipes

    init {
        getFavoriteIds()
    }

    private fun getFavoriteIds() {
        viewModelScope.launch(Dispatchers.IO) {
            favoritesManager.getFavoriteIdList().collect { ids ->
                favoriteIdList = ids
                searchFoodsByIds()
            }
        }
    }

    fun removeFavoriteId(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            favoritesManager.removeFavoriteId(id)
            searchFoodsByIds()
        }
    }

    private suspend fun searchFoodsByIds() {
        _favoriteRecipes.value = FavoriteRecipesState(loader = true)
        try {
            val ids = favoriteIdList.joinToString(",")
            val result = spoonacularRepository.searchFoodsByIds(ids).map { it.toRecipe() }
            _favoriteRecipes.value =
                FavoriteRecipesState(favoriteRecipes = result, loader = false)
        } catch (e: Throwable) {
            _favoriteRecipes.value = FavoriteRecipesState(error = e.message, loader = false)
        }

    }
}