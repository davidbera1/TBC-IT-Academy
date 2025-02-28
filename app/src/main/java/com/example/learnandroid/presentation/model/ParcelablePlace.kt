package com.example.learnandroid.presentation.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ParcelablePlace(
    val latitude: Double,
    val longitude: Double,
    val placeTitle: String,
    val address: String
) : Parcelable