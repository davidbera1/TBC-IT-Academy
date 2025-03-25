package com.example.learnandroid.domain.use_case

import android.net.Uri
import androidx.camera.view.LifecycleCameraController
import com.example.learnandroid.domain.repository.CameraRepository
import javax.inject.Inject

class TakePhotoUseCase @Inject constructor(
    private val repository: CameraRepository
) {
    suspend operator fun invoke(controller: LifecycleCameraController): Result<Uri> {
        return repository.takePhoto(controller)
    }
}
