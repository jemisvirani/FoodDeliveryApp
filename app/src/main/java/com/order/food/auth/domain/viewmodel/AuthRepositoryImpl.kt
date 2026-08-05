package com.order.food.auth.domain.viewmodel

import android.util.Log
import com.order.food.auth.domain.repository.AuthRepository
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

        return runCatching {

            val result = auth
                .createUserWithEmailAndPassword(email, password)
                .await()

            val uid = result.user!!.uid

            val user = hashMapOf(
                "uid" to uid,
                "fullName" to fullName,
                "email" to email,
                "address" to address
            )

            firestore
                .collection("users")
                .document(uid)
                .set(user)
                .await()
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

    override suspend fun forgotPassword(
        email: String
    ): Result<Unit> {

        return try {

            auth.sendPasswordResetEmail(email).await()

            Result.success(Unit)

        } catch (e: Exception) {

            Result.failure(e)
        }
    }

    override suspend fun isUserRegistered(email: String): Boolean {
        val snapshot = firestore.collection("users")
            .whereEqualTo("email", email)
            .get()
            .await()

        return !snapshot.isEmpty
    }

    override suspend fun changePassword(
        newPassword: String
    ): Result<Unit> {
        return try {
            val user = FirebaseAuth.getInstance().currentUser
                ?: return Result.failure(Exception("User not logged in"))

            user.updatePassword(newPassword).await()

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}