package com.example.learnandroid.data.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.camera.core.ImageProxy
import java.io.ByteArrayOutputStream

fun ImageProxy.toBitmap(): Bitmap {
    val buffer = planes[0].buffer
    val bytes = ByteArray(buffer.remaining())
    buffer.get(bytes)
    val originalBitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
    val outputStream = ByteArrayOutputStream()
    originalBitmap.compress(Bitmap.CompressFormat.JPEG, 80, outputStream)
    val compressedBytes = outputStream.toByteArray()

    return BitmapFactory.decodeByteArray(compressedBytes, 0, compressedBytes.size)
}