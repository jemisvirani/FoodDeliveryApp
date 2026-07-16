package com.food.delivery.auth.ui.state

data class ChangePasswordUiState(

    val password: String = "",
    val confirmPassword: String = "",

    val passwordError: String? = null,
    val confirmPasswordError: String? = null

)