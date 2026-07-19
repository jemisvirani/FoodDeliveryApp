package com.food.delivery.auth.ui.state

data class ChangePasswordUiState(
    val newPassword: String = "",
    val confirmPassword: String = "",

    val newPasswordError: String? = null,
    val confirmPasswordError: String? = null,

    val isLoading: Boolean = false,
    val snackBar: String? = null
)