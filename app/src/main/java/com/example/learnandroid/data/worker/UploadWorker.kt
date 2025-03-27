package com.example.learnandroid.data.worker

import android.content.Context
import android.net.Uri
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.tasks.await

class UploadWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        val fileUri = inputData.getString("image_uri") ?: return Result.failure()

        return try {
            val uri = Uri.parse(fileUri)
            val fileName = "uploads/${System.currentTimeMillis()}.jpg"
            val storageRef = Firebase.storage.reference.child(fileName)
            storageRef.putFile(uri).await()
            Result.success()
        } catch (e: Throwable) {
            Result.failure(workDataOf("error_message" to e.message))
        }
    }
}
