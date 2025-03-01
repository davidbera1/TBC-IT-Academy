package com.example.learnandroid.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.learnandroid.R

fun ImageView.loadImage(url: String?) {
    Glide.with(this.context)
        .load(url)
        .placeholder(R.drawable.placeholder)
        .error(R.drawable.error)
        .into(this)
}