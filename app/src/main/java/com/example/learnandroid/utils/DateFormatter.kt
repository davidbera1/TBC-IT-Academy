package com.example.learnandroid.utils

import java.text.SimpleDateFormat
import java.util.Date

fun Long.toDate() : String {
    val formatter = SimpleDateFormat("dd MMMM 'at' h:mm a")
    return formatter.format(Date(this))
}