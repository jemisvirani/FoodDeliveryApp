package com.order.food.auth.domain.repository

interface AuthRepository {
    suspend fun signUp(
        fullName: String,
        email: String,
        address: String,
        password: String
    ): Result<Unit>

    suspend fun login(
        email: String,
        password: String
    ): Result<Unit>

    suspend fun forgotPassword(
        email: String
    ): Result<Unit>

    suspend fun isUserRegistered(email: String): Boolean

    suspend fun changePassword(
        password: String
    ): Result<Unit>


}