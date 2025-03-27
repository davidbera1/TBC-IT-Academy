package com.example.learnandroid.data.repository

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.view.LifecycleCameraController
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.example.learnandroid.data.util.rotate
import com.example.learnandroid.domain.repository.CameraRepository
import kotlinx.coroutines.suspendCancellableCoroutine
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class CameraRepositoryImpl @Inject constructor(
    private val context: Context
) : CameraRepository {

    override suspend fun takePhoto(controller: LifecycleCameraController): Result<Uri> {
        return try {
            val imageProxy = captureImage(controller)
            val uri = saveToCache(imageProxy)
            imageProxy.close()
            Result.success(uri)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private suspend fun captureImage(controller: LifecycleCameraController): ImageProxy =
        suspendCancellableCoroutine { cont ->
            controller.takePicture(
                ContextCompat.getMainExecutor(context),
                object : ImageCapture.OnImageCapturedCallback() {
                    override fun onCaptureSuccess(image: ImageProxy) {
                        cont.resume(image)
                    }

                    override fun onError(exception: ImageCaptureException) {
                        cont.resumeWithException(exception)
                    }
                }
            )
        }

    private fun saveToCache(image: ImageProxy): Uri {
        val buffer = image.planes[0].buffer
        val bytes = ByteArray(buffer.remaining())
        buffer.get(bytes)

        val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
        val rotatedBitmap = bitmap.rotate(image.imageInfo.rotationDegrees)

        val filename = "captured_${System.currentTimeMillis()}.jpg"
        val file = File(context.cacheDir, filename)

        FileOutputStream(file).use { out ->
            rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, 80, out)
        }

        return FileProvider.getUriForFile(
            context,
            "${context.packageName}.provider",
            file
        )
    }
}
