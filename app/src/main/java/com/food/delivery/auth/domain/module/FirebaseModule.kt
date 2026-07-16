package com.food.delivery.auth.domain.module

import com.food.delivery.auth.domain.repository.AuthRepository
import com.food.delivery.auth.domain.viewmodel.AuthRepositoryImpl
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {

        return FirebaseAuth.getInstance()

    }

    @Provides
    @Singleton
    fun provideRepository(
        auth: FirebaseAuth
    ): AuthRepository {

        return AuthRepositoryImpl(auth)

    }

}