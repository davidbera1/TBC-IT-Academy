package com.example.learnandroid.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.learnandroid.data.model.dto.UsersDto
import com.example.learnandroid.data.remote.common.ApiHelper
import com.example.learnandroid.data.remote.common.Resource

class UsersPagingSource(private val userService: UserService) : PagingSource<Int, UsersDto.Data>() {

    override fun getRefreshKey(state: PagingState<Int, UsersDto.Data>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UsersDto.Data> {
        val page = params.key ?: 1

        val response = ApiHelper.handleHttpRequest {
            userService.getUsers(page)
        }
        return when(response) {
            is Resource.Success -> {
                val users = response.data.data

                LoadResult.Page(
                    data = users,
                    prevKey = if (page == 1) null else page - 1,
                    nextKey = if (users.isEmpty()) null else page + 1
                )
            }
            is Resource.Error -> {
                LoadResult.Error(Throwable(response.error))
            }
        }
    }
}
