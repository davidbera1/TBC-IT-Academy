package com.example.learnandroid.data.repository

import com.example.learnandroid.data.mapper.toDomain
import com.example.learnandroid.data.remote.CategoryService
import com.example.learnandroid.data.remote.common.ApiHelper
import com.example.learnandroid.domain.common.Resource
import com.example.learnandroid.domain.common.mapResource
import com.example.learnandroid.domain.model.Category
import com.example.learnandroid.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val categoryService: CategoryService,
    private val apiHelper: ApiHelper
) : CategoryRepository {

    override fun getCategories(): Flow<Resource<List<Category>>> {
        return apiHelper.handleHttpRequest {
            categoryService.getCategories()
        }.mapResource {
            it.map { category ->
                category.toDomain()
            }
        }
    }
}