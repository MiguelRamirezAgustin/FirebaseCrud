package com.example.crudfirebase.appFirebase

import com.example.crudfirebase.appFirebase.data.remote.FirebaseAuthService
import com.example.crudfirebase.appFirebase.data.repository.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideAuthService(): FirebaseAuthService {
        return FirebaseAuthService()
    }

    @Provides
    @Singleton
    fun provideRepository(
        service: FirebaseAuthService
    ): AuthRepository {
        return AuthRepository(service)
    }
}