package com.example.learnandroid.domain.use_case

import android.net.Uri
import com.example.learnandroid.domain.repository.UploadRepository
import java.util.UUID
import javax.inject.Inject

class UploadImageUseCase @Inject constructor(
    private val repository: UploadRepository
) {
    suspend operator fun invoke(uri: Uri): Result<UUID> {
        return repository.uploadImage(uri)
    }
}