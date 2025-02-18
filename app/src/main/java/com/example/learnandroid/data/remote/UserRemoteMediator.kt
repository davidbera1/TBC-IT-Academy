package com.example.learnandroid.data.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.example.learnandroid.data.local.room.UserDao
import com.example.learnandroid.data.local.room.entity.User

@OptIn(ExperimentalPagingApi::class)
class UserRemoteMediator(
    private val userService: UserService,
    private val userDao: UserDao,
    private val perPage: Int = 6
) : RemoteMediator<Int, User>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, User>
    ): MediatorResult {
        return try {
            val page = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    lastItem?.let { it.id / perPage + 1 } ?: 1
                }
            }

            val response = userService.getUsers(page, perPage)
            if (!response.isSuccessful) {
                return MediatorResult.Error(Throwable("Error"))
            }

            val users = response.body()?.data?.map { user ->
                User(
                    id = user.id,
                    email = user.email,
                    firstName = user.firstName,
                    lastName = user.lastName,
                    avatar = user.avatar
                )
            } ?: emptyList()

            if (loadType == LoadType.REFRESH) {
                userDao.clearUsers()
            }

            userDao.insertUsers(users)

            val endOfPaginationReached = users.size < perPage
            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: Throwable) {
            MediatorResult.Error(e)
        }
    }
}
