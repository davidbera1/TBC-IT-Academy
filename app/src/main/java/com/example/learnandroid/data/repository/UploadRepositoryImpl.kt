package com.example.learnandroid.data.repository

import android.content.Context
import android.net.Uri
import androidx.work.Data
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.learnandroid.data.worker.UploadWorker
import com.example.learnandroid.domain.repository.UploadRepository
import java.util.UUID
import javax.inject.Inject

class UploadRepositoryImpl @Inject constructor(
    private val applicationContext: Context
) : UploadRepository {

    override suspend fun uploadImage(uri: Uri): Result<UUID> {
        return try {
            val inputData = Data.Builder()
                .putString("image_uri", uri.toString())
                .build()

            val request = OneTimeWorkRequestBuilder<UploadWorker>()
                .setInputData(inputData)
                .addTag("upload_tag")
                .build()

            WorkManager.getInstance(applicationContext).enqueueUniqueWork(
                "upload_image_work",
                ExistingWorkPolicy.KEEP,
                request
            )

            Result.success(request.id)
        } catch (e: Throwable) {
            Result.failure(e)
        }
    }

}