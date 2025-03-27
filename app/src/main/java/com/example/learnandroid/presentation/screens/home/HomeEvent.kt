package com.example.learnandroid.presentation.screens.home

import android.net.Uri

sealed class HomeEvent {
    data object AddImageButtonClicked : HomeEvent()
    data class ImageAdded(val uri: Uri) : HomeEvent()
    data class UploadImage(val uri: Uri) : HomeEvent()
    data object UploadSucceeded : HomeEvent()
    data object UploadFailed : HomeEvent()
}