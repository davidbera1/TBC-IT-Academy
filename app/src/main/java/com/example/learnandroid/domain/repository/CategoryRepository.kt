package com.example.learnandroid.domain.repository

import com.example.learnandroid.domain.common.Resource
import com.example.learnandroid.domain.model.Category
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    fun getCategories(): Flow<Resource<List<Category>>>
}