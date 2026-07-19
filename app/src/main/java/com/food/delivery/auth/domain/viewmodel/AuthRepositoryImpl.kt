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
            val authResult = auth
                .createUserWithEmailAndPassword(email, password)
                .await()

            val uid = authResult.user!!.uid

            val user = User(
                uid = uid,
                fullName = fullName,
                email = email,
                address = address
            )

            firestore.collection("users")
                .document(uid)
                .set(user)
                .await()


            Result.success(Unit)

        } catch (e: Exception) {
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

    override suspend fun forgotPassword(
        email: String
    ): Result<Unit> {
        return try {
            Log.d("ForgetPasswordDeliveryApp", email)
            auth.sendPasswordResetEmail(email).await()
            Log.d("ForgetPasswordDeliveryApp", email)

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