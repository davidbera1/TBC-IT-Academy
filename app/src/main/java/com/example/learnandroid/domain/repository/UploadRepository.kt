package com.example.learnandroid.domain.repository

import android.net.Uri
import java.util.UUID

interface UploadRepository {
    suspend fun uploadImage(uri: Uri): Result<UUID>
}