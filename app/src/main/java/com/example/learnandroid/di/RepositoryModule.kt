package com.example.learnandroid.di

import com.example.learnandroid.data.repository.CameraRepositoryImpl
import com.example.learnandroid.data.repository.UploadRepositoryImpl
import com.example.learnandroid.domain.repository.CameraRepository
import com.example.learnandroid.domain.repository.UploadRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindCameraRepository(cameraRepositoryImpl: CameraRepositoryImpl): CameraRepository

    @Binds
    @Singleton
    abstract fun bindUploadRepository(uploadRepositoryImpl: UploadRepositoryImpl): UploadRepository
}
