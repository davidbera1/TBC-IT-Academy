package com.example.learnandroid.presentation.util

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.learnandroid.R

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.GONE
}

fun ImageView.loadImage(url: String?) {
    Glide.with(context)
        .load(url)
        .placeholder(R.drawable.placeholder)
        .error(R.drawable.error)
        .into(this)
}