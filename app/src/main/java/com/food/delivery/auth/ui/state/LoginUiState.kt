package com.food.delivery.auth.ui.state

data class LoginUiState(

    val email: String = "",
    val password: String = "",

    val rememberMe: Boolean = false,

    val emailError: String? = null,
    val passwordError: String? = null,

    val isLoading: Boolean = false,
    val snackBar: String? = null
)

