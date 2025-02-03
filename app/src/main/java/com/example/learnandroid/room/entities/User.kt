package com.example.learnandroid.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey val id: Int,
    val firstName: String,
    val lastName: String,
    val profileImage: String?,
    val activationStatus: Int,
    val about: String?
)