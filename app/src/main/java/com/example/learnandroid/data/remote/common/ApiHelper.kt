package com.example.learnandroid.data.remote.common

import okio.IOException
import retrofit2.HttpException
import retrofit2.Response

object ApiHelper {
    suspend fun <T> handleHttpRequest(apiCall: suspend () -> Response<T>): Resource<T> {
        val response = apiCall.invoke()
        try {
            return if (response.isSuccessful) {
                response.body()?.let {
                    Resource.Success(data = it)
                } ?: Resource.Error(error = "Something went wrong")
            } else {
                Resource.Error(error = response.errorBody()?.string() ?: "Something went wrong")
            }
        } catch (e: Throwable) {
            return when (e) {
                is IOException -> Resource.Error(error = e.message ?: "IO Error")
                is HttpException -> Resource.Error(error = e.message ?: "HTTP Error")
                is IllegalStateException -> Resource.Error(error = e.message ?: "Illegal State Error")
                else -> Resource.Error(error = e.message ?: "Error")
            }
        }
    }
}