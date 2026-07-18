package com.food.delivery.auth.domain.module

import com.food.delivery.auth.domain.repository.AuthRepository
import com.food.delivery.auth.domain.viewmodel.AuthRepositoryImpl
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
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
        auth: FirebaseAuth,
        firestore: FirebaseFirestore
    ): AuthRepository {

        return AuthRepositoryImpl(auth,firestore)

    }

    @Provides
    @Singleton
    fun provideFirestore(): FirebaseFirestore {

        return FirebaseFirestore.getInstance()

    }

}