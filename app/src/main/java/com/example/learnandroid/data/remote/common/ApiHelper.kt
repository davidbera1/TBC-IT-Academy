package com.example.learnandroid.data.remote.common

import com.example.learnandroid.domain.common.Resource
import okio.IOException
import org.json.JSONObject
import retrofit2.HttpException
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiHelper @Inject constructor() {

    suspend fun <T> handleHttpRequest(apiCall: suspend () -> Response<T>): Resource<T> {
        return try {
            val response = apiCall.invoke()
            if (response.isSuccessful) {
                response.body()?.let {
                    Resource.Success(data = it)
                } ?: Resource.Error(error = "Something went wrong")
            } else {
                val errorBody = response.errorBody()?.string()
                val jsonObject = JSONObject(errorBody ?: "")
                val errorMessage = jsonObject.optString("error", "An unknown error occurred")
                Resource.Error(error = errorMessage)
            }
        } catch (e: IOException) {
            Resource.Error(error = "Network error. Please check your internet connection.")
        } catch (e: HttpException) {
            Resource.Error(error = "Server error: ${e.code()}")
        } catch (e: Exception) {
            Resource.Error(error = e.message ?: "Unexpected error occurred")
        }
    }
}
