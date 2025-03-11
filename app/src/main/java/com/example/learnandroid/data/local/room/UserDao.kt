package com.example.learnandroid.data.local.room

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.learnandroid.data.local.room.entity.UserEntity

@Dao
interface UserDao {
    @Query("SELECT * FROM users ORDER BY id ASC")
    fun getUsers(): PagingSource<Int, UserEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsers(users: List<UserEntity>)

    @Query("DELETE FROM users")
    suspend fun clearUsers()
}