package com.example.learnandroid.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.learnandroid.data.local.room.AppDatabase
import com.example.learnandroid.data.mapper.toUser
import com.example.learnandroid.data.remote.UserRemoteMediator
import com.example.learnandroid.data.remote.UserService
import com.example.learnandroid.domain.model.User
import com.example.learnandroid.domain.repository.UsersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UsersRepositoryImpl @Inject constructor(
    private val database: AppDatabase,
    private val userService: UserService
) : UsersRepository {

    override fun getUsers(): Flow<PagingData<User>> {
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = 6,
                initialLoadSize = 6,
                prefetchDistance = 0
            ),
            remoteMediator = UserRemoteMediator(userService, database.userDao()),
            pagingSourceFactory = { database.userDao().getUsers() }
        ).flow.map { pagingData -> pagingData.map { it.toUser() } }
    }
}