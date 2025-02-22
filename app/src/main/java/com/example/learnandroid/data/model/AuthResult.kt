package com.example.learnandroid.data.model

import com.google.firebase.auth.FirebaseUser

data class AuthResult(
    val result: Result<FirebaseUser>? = null,
    val loader: Boolean
)