package com.example.learnandroid.room

import android.content.Context
import com.example.learnandroid.client.RetrofitClient.apiService
import com.example.learnandroid.model.ResponseDto
import com.example.learnandroid.room.entities.User
import kotlinx.coroutines.flow.Flow

class UserRepository(context: Context) {

    private val userDao: UserDao = AppDatabase.DatabaseProvider.getDatabase(context).userDao()

    fun getUsersFromDB(): Flow<List<User>> = userDao.getAllUsers()

    suspend fun fetchUsersFromApi(): ResponseDto? {
        try {
            val response = apiService.getUsers()

            if (response.isSuccessful) {
                response.body()?.let { responseDto ->
                    userDao.insertUsers(responseDto.users.map { it.toUserEntity() })
                    return responseDto
                }
            } else {
                return null
            }
        } catch (e: Throwable) {
            return null
        }

        return null
    }
}
