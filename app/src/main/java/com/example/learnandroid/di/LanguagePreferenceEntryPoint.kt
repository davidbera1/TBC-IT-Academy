package com.example.learnandroid.di

import com.example.learnandroid.data.local.datastore.LanguagePreferencesManager
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface LanguagePreferenceEntryPoint {
    fun getLanguagePreference(): LanguagePreferencesManager
}