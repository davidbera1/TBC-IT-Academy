package com.example.learnandroid.domain.use_case

import android.net.Uri
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class GetImagesUseCase @Inject constructor() {
    suspend operator fun invoke(): Result<List<Uri>> {
        return try {
            val storageRef = Firebase.storage.reference.child("uploads")
            val result = storageRef.listAll().await()
            val uris = result.items.map { it.downloadUrl.await() }
            Result.success(uris)
        } catch (e: Throwable) {
            Result.failure(e)
        }
    }
}