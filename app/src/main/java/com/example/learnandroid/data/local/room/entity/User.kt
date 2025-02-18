package com.example.learnandroid.data.local.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = false) val id: Int,
    val email: String,
    val firstName: String,
    val lastName: String,
    val avatar: String
)