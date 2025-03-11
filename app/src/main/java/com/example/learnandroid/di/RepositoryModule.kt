package com.example.learnandroid.di

import com.example.learnandroid.data.repository.LoginRepositoryImpl
import com.example.learnandroid.data.repository.RegisterRepositoryImpl
import com.example.learnandroid.domain.repository.LoginRepository
import com.example.learnandroid.domain.repository.RegisterRepository
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
    abstract fun bindLoginRepository(loginRepositoryImpl: LoginRepositoryImpl): LoginRepository

    @Binds
    @Singleton
    abstract fun bindRegisterRepository(registerRepositoryImpl: RegisterRepositoryImpl): RegisterRepository
}