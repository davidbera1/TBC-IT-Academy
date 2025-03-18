package com.example.learnandroid.domain.use_case

import com.example.learnandroid.domain.common.Resource
import com.example.learnandroid.domain.model.Category
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform
import javax.inject.Inject

class GetFilteredCategoriesUseCase @Inject constructor(
    private val getAllCategoriesUseCase: GetAllCategoriesUseCase
) {
    operator fun invoke(query: String): Flow<Resource<List<Category>>> {
        return getAllCategoriesUseCase().transform { resource ->
            when (resource) {
                is Resource.Success -> {
                    val filteredCategories = resource.data.filter {
                        it.name.contains(query, ignoreCase = true)
                    }
                    emit(Resource.Success(filteredCategories))
                }

                is Resource.Loader -> {
                    emit(Resource.Loader(resource.isLoading))
                }

                is Resource.Error -> {
                    emit(Resource.Error(resource.errorMessage))
                }
            }
        }
    }
}
