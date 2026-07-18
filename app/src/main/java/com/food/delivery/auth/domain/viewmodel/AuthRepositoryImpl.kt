package com.food.delivery.auth.domain.viewmodel

import android.util.Log
import com.food.delivery.auth.domain.model.User
import com.food.delivery.auth.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import jakarta.inject.Inject
import kotlinx.coroutines.tasks.await

class AuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : AuthRepository {

    override suspend fun signUp(
        fullName: String,
        email: String,
        address: String,
        password: String
    ): Result<Unit> {

        return try {

            Log.d("AuthRepositoryImpl", "Before Auth")

            val authResult = auth
                .createUserWithEmailAndPassword(email, password)
                .await()

            Log.d("AuthRepositoryImpl", "After Auth")

            val uid = authResult.user!!.uid

            val user = User(
                uid = uid,
                fullName = fullName,
                email = email,
                address = address
            )

            Log.d("AuthRepositoryImpl", "Before Firestore")

            firestore.collection("users")
                .document(uid)
                .set(user)
                .await()

            Log.d("AuthRepositoryImpl", "After Firestore")

            Result.success(Unit)

        } catch (e: Exception) {
            Log.e("AuthRepositoryImpl", "Exception", e)
            Result.failure(e)
        }
    }

    override suspend fun login(
        email: String,
        password: String
    ): Result<Unit> {

        return try {

            auth.signInWithEmailAndPassword(email, password).await()

            Result.success(Unit)

        } catch (e: Exception) {

            Result.failure(e)
        }
    }
}