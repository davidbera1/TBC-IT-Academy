package com.example.myapplication

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
enum class LevelType : Parcelable {
    GAME3x3,
    GAME4x4,
    GAME5x5
}