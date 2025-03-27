package com.example.learnandroid.presentation.screens.camera

import android.net.Uri

data class CameraState(
    val photoUri: Uri? = null,
    val error: String? = null
)