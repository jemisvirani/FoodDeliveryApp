package com.food.delivery.auth.ui.state

data class SignUpUiState(

    val fullName: String = "",
    val email: String = "",
    val address: String = "",
    val password: String = "",
    val confirmPassword: String = "",

    val fullNameError: String? = null,
    val emailError: String? = null,
    val addressError: String? = null,
    val passwordError: String? = null,
    val confirmPasswordError: String? = null,

    val isLoading: Boolean = false,
    val snackBar: String? = null
)