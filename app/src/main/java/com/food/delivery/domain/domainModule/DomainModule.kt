package com.food.delivery.domain.domainModule

import com.food.delivery.data.repoImpl.RepoImpl
import com.food.delivery.domain.repo.Repo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Provides
    fun provideRepo(firebaseAuth: FirebaseAuth,firebaseFireStore : FirebaseFirestore) : Repo{
        return RepoImpl(firebaseAuth, firebaseFireStore)
    }
}