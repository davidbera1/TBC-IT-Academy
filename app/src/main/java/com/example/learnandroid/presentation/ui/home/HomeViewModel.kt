package com.example.learnandroid.presentation.ui.home

import androidx.lifecycle.viewModelScope
import com.example.learnandroid.domain.common.Resource
import com.example.learnandroid.domain.use_case.GetAllCategoriesUseCase
import com.example.learnandroid.domain.use_case.GetFilteredCategoriesUseCase
import com.example.learnandroid.presentation.base.BaseViewModel
import com.example.learnandroid.presentation.mapper.toPresentation
import com.example.learnandroid.presentation.model.CategoryUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAllCategoriesUseCase: GetAllCategoriesUseCase,
    private val getFilteredCategoriesUseCase: GetFilteredCategoriesUseCase
) : BaseViewModel<HomeState, HomeEvent, HomeEffect>(HomeState()) {

    init {
        observeSearchQuery()
    }

    private fun observeSearchQuery() {
        state
            .map { it.searchQuery }
            .debounce(300)
            .distinctUntilChanged()
            .onEach { query ->
                if (query.isNotBlank()) {
                    searchCategory(query)
                } else {
                    getAllCategories()
                }
            }
            .launchIn(viewModelScope)
    }

    private fun searchCategory(query: String) {
        viewModelScope.launch {
            getFilteredCategoriesUseCase(query).collect { result ->
                when (result) {
                    is Resource.Error -> emitEffect(HomeEffect.ShowToast(result.errorMessage))
                    is Resource.Loader -> {
                        updateState { copy(isLoading = result.isLoading) }
                    }
                    is Resource.Success -> {
                        val categoriesWithDepth =
                            calculateCategoryDepth(result.data.map { it.toPresentation() })
                        updateState { copy(categories = categoriesWithDepth) }
                    }
                }
            }
        }
    }

    private fun getAllCategories() {
        viewModelScope.launch {
            getAllCategoriesUseCase().collect { result ->
                when (result) {
                    is Resource.Error -> emitEffect(HomeEffect.ShowToast(result.errorMessage))
                    is Resource.Loader -> updateState { copy(isLoading = result.isLoading) }
                    is Resource.Success -> {
                        val categoriesWithDepth =
                            calculateCategoryDepth(result.data.map { it.toPresentation() })
                        updateState { copy(categories = categoriesWithDepth) }
                    }
                }
            }
        }
    }

    override fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.GetAllCategories -> getAllCategories()
            is HomeEvent.UpdateSearchQuery -> {
                updateState { copy(searchQuery = event.query) }
            }
        }
    }

    private fun calculateCategoryDepth(
        categories: List<CategoryUi>,
        currentDepth: Int = 0
    ): List<Pair<CategoryUi, Int>> {
        val result = mutableListOf<Pair<CategoryUi, Int>>()

        for (category in categories) {
            result.add(Pair(category, currentDepth))
            if (category.children.isNotEmpty()) {
                result.addAll(calculateCategoryDepth(category.children, currentDepth + 1))
            }
        }

        return result
    }
}