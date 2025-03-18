package com.example.learnandroid.domain.use_case

import com.example.learnandroid.domain.common.Resource
import com.example.learnandroid.domain.model.Category
import com.example.learnandroid.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllCategoriesUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository
) {
    operator fun invoke(): Flow<Resource<List<Category>>> {
        return categoryRepository.getCategories()
    }
}