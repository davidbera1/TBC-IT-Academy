package com.example.learnandroid.data.repository

import com.example.learnandroid.data.local.datastore.DataStoreManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseRepository @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val dataStoreManager: DataStoreManager
) {

    suspend fun login(email: String, password: String): Result<FirebaseUser> {
        return try {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            dataStoreManager.saveEmail(email)
            Result.success(result.user!!)
        } catch (e: Throwable) {
            Result.failure(e)
        }
    }

    suspend fun register(email: String, password: String): Result<FirebaseUser> {
        return try {
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            Result.success(result.user!!)
        } catch (e: Throwable) {
            Result.failure(e)
        }
    }

    fun logout() {
        firebaseAuth.signOut()
    }

    fun getUserSession(): FirebaseUser? {
        return firebaseAuth.currentUser
    }

    fun clearUserSession() {
        logout()
    }
}