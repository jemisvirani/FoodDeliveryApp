package com.order.food.auth.ui.components.login

data class LoginUiState(

    val email: String = "",
    val password: String = "",

    val emailError: String? = null,
    val passwordError: String? = null,

    val isPasswordVisible: Boolean = false,

    val rememberMe: Boolean = false,

    val isLoading: Boolean = false,

    val error: String? = null

) {

    val isFormValid: Boolean
        get() =
            email.isNotBlank() &&
                    password.isNotBlank() &&
                    emailError == null &&
                    passwordError == null
}