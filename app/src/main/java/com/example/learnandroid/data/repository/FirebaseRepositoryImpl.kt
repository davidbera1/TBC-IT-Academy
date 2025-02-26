package com.example.learnandroid.data.repository

import com.example.learnandroid.data.local.datastore.DataStoreManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val dataStoreManager: DataStoreManager
) : FirebaseRepository {

    override suspend fun login(email: String, password: String): Result<FirebaseUser> {
        return try {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            dataStoreManager.saveEmail(email)
            Result.success(result.user!!)
        } catch (e: Throwable) {
            Result.failure(e)
        }
    }

    override suspend fun register(email: String, password: String): Result<FirebaseUser> {
        return try {
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            Result.success(result.user!!)
        } catch (e: Throwable) {
            Result.failure(e)
        }
    }

    override fun logout() {
        firebaseAuth.signOut()
    }

    override fun getUserSession(): FirebaseUser? {
        return firebaseAuth.currentUser
    }

    override fun clearUserSession() {
        logout()
    }
}