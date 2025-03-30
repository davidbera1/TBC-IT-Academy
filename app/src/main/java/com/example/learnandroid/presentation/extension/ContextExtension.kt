package com.example.learnandroid.presentation.extension

import android.content.Context
import android.widget.Toast

fun Context.showToast(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}