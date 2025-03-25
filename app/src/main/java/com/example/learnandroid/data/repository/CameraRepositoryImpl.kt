package com.example.learnandroid.data.repository

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Matrix
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.view.LifecycleCameraController
import androidx.core.content.ContextCompat
import com.example.learnandroid.R
import com.example.learnandroid.data.util.toBitmap
import com.example.learnandroid.domain.repository.CameraRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class CameraRepositoryImpl @Inject constructor(
    private val context: Context
) : CameraRepository {

    override suspend fun takePhoto(controller: LifecycleCameraController): Result<Uri> =
        suspendCoroutine { continuation ->

            controller.takePicture(
                ContextCompat.getMainExecutor(context),
                object : ImageCapture.OnImageCapturedCallback() {
                    override fun onCaptureSuccess(image: ImageProxy) {
                        val matrix = Matrix().apply {
                            postRotate(image.imageInfo.rotationDegrees.toFloat())
                        }

                        val imageBitmap = Bitmap.createBitmap(
                            image.toBitmap(),
                            0, 0,
                            image.width, image.height,
                            matrix, true
                        )

                        image.close()

                        CoroutineScope(Dispatchers.IO).launch {
                            try {
                                val uri = savePhoto(imageBitmap)
                                continuation.resume(Result.success(uri))
                            } catch (e: Exception) {
                                continuation.resume(Result.failure(e))
                            }
                        }
                    }

                    override fun onError(exception: ImageCaptureException) {
                        continuation.resume(Result.failure(exception))
                    }
                }
            )
        }

    private suspend fun savePhoto(bitmap: Bitmap): Uri {
        return withContext(Dispatchers.IO) {
            val resolver = context.contentResolver
            val imageCollection =
                MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
            val appName = context.getString(R.string.app_name)
            val timeInMillis = System.currentTimeMillis()

            val values = ContentValues().apply {
                put(MediaStore.Images.Media.DISPLAY_NAME, "${timeInMillis}_image.jpg")
                put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DCIM + "/$appName")
                put(MediaStore.Images.Media.MIME_TYPE, "image/jpg")
                put(MediaStore.MediaColumns.DATE_TAKEN, timeInMillis)
                put(MediaStore.MediaColumns.IS_PENDING, 1)
            }

            val uri = resolver.insert(imageCollection, values)
                ?: throw IOException("Failed to create new MediaStore record.")

            resolver.openOutputStream(uri)?.use { out ->
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
            } ?: throw IOException("Failed to open output stream.")

            values.clear()
            values.put(MediaStore.MediaColumns.IS_PENDING, 0)
            resolver.update(uri, values, null, null)

            uri
        }
    }
}