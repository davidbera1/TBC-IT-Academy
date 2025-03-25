package com.example.learnandroid.presentation.screens.camera

import androidx.camera.view.LifecycleCameraController

sealed class CameraEvent {
    data class TakePhoto(val controller: LifecycleCameraController) : CameraEvent()
}
