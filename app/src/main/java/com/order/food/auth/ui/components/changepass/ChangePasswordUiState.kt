package com.order.food.auth.ui.components.changepass

import com.order.food.auth.ui.components.signup.PasswordStrength

data class ChangePasswordUiState(

    val password: String = "",
    val confirmPassword: String = "",

    val passwordError: String? = null,
    val confirmPasswordError: String? = null,

    val passwordStrength: PasswordStrength = PasswordStrength.NONE,

    val isPasswordVisible: Boolean = false,
    val isConfirmPasswordVisible: Boolean = false,

    val isLoading: Boolean = false,

    val error: String? = null

) {

    val isFormValid: Boolean
        get() =
            password.isNotBlank() &&
                    confirmPassword.isNotBlank() &&
                    passwordError == null &&
                    confirmPasswordError == null
}