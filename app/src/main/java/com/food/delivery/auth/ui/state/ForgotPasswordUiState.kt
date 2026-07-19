package com.food.delivery.auth.ui.state

data class ForgotPasswordUiState(
    val email: String = "",
    val emailError: String? = null,
    val isLoading: Boolean = false,
    val snackBar: String? = null
)