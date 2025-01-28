package com.example.learnandroid.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.learnandroid.client.UserService
import com.example.learnandroid.model.dto.UsersDto

class UsersPagingSource(private val userService: UserService) : PagingSource<Int, UsersDto.Data>() {

    override fun getRefreshKey(state: PagingState<Int, UsersDto.Data>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UsersDto.Data> {
        val page = params.key ?: 1

        return try {
            val response = userService.getUsers(page)

            if (response.isSuccessful) {
                val users = response.body()?.data.orEmpty()

                LoadResult.Page(
                    data = users,
                    prevKey = if (page == 1) null else page - 1,
                    nextKey = if (users.isEmpty()) null else page + 1
                )
            } else {
                LoadResult.Error(Throwable("${response.code()}"))
            }
        } catch (e: Throwable) {
            LoadResult.Error(e)
        }
    }
}
