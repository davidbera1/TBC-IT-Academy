package com.example.learnandroid.data.remote.common

import com.example.learnandroid.domain.common.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import org.json.JSONObject
import retrofit2.HttpException
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
                    val errorBody = response.errorBody()?.string()
                    val jsonObject = JSONObject(errorBody ?: "")
                    val errorMessage = jsonObject.optString("error", "An unknown error occurred")
                    emit(Resource.Error(errorMessage = errorMessage))
                }
            } catch (e: Throwable) {
                val errorMessage = when (e) {
                    is IOException -> e.message ?: "Error"
                    is HttpException -> e.message ?: "Error"
                    is IllegalStateException -> e.message ?: "Error"
                    else -> e.message ?: "Error"
                }
                emit(Resource.Error(errorMessage = errorMessage))
            }

            emit(Resource.Loader(isLoading = false))
        }
    }
}