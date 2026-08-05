package com.order.food.auth.ui.components.signup

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

    val passwordStrength: PasswordStrength = PasswordStrength.NONE,

    val isPasswordVisible: Boolean = false,
    val isConfirmPasswordVisible: Boolean = false,

    val isLoading: Boolean = false,

    val error: String? = null

) {

    val isFormValid: Boolean
        get() =
            fullName.isNotBlank() &&
                    email.isNotBlank() &&
                    address.isNotBlank() &&
                    password.isNotBlank() &&
                    confirmPassword.isNotBlank() &&
                    fullNameError == null &&
                    emailError == null &&
                    addressError == null &&
                    passwordError == null &&
                    confirmPasswordError == null
}