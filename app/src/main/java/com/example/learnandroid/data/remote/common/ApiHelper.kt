package com.example.learnandroid.data.remote.common

import com.example.learnandroid.domain.common.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiHelper @Inject constructor() {

    fun <T> handleHttpRequest(apiCall: suspend () -> Response<T>): Flow<Resource<T>> {
        return flow {
            emit(Resource.Loader(isLoading = true))

            val response = apiCall.invoke()
            try {
                if (response.isSuccessful) {
                    emit(response.body()?.let {
                        Resource.Success(data = it)
                    } ?: Resource.Error(errorMessage = "Something went wrong"))
                } else {
                    emit(Resource.Error(errorMessage = response.errorBody().toString()))
                }
            } catch (e: Throwable) {
                emit(Resource.Error(errorMessage = e.message ?: "Error"))
            }

            emit(Resource.Loader(isLoading = false))
        }
    }
}