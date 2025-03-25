package com.example.learnandroid.presentation.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.core.content.FileProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.io.File

suspend fun compressUri(context: Context, uri: Uri): Uri = withContext(Dispatchers.IO) {
    val inputStream = context.contentResolver.openInputStream(uri)
    val originalBitmap = BitmapFactory.decodeStream(inputStream)
    inputStream?.close()

    val outputStream = ByteArrayOutputStream()
    originalBitmap.compress(Bitmap.CompressFormat.JPEG, 80, outputStream)

    val compressedBytes = outputStream.toByteArray()

    val file = File.createTempFile("compressed_", ".jpg", context.cacheDir)
    file.outputStream().use { it.write(compressedBytes) }

    FileProvider.getUriForFile(
        context,
        "${context.packageName}.provider",
        file
    )
}

