package com.example.learnandroid.presentation.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Ingredient(
    val name: String,
    val amount: Double? = null,
    val unit: String? = null
) : Parcelable