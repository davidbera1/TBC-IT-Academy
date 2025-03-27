package com.example.learnandroid.presentation.screens.home

import android.net.Uri

data class HomeState(
    val imageList: List<Uri> = emptyList(),
    val isUploading: Boolean = false
)