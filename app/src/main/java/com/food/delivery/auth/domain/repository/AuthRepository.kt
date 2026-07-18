package com.food.delivery.auth.domain.repository

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

}