package com.example.learnandroid.data.repository

import com.google.firebase.auth.FirebaseUser

interface FirebaseRepository {
    suspend fun login(email: String, password: String): Result<FirebaseUser>
    suspend fun register(email: String, password: String): Result<FirebaseUser>
    fun logout()
    fun getUserSession(): FirebaseUser?
    fun clearUserSession()
}