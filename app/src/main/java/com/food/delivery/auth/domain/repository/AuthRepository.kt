package com.food.delivery.auth.domain.repository

interface AuthRepository {
    suspend fun signUp(
        email: String,
        password: String
    ): Result<Unit>

}