package com.order.food.auth.ui.components.forget

data class ForgotPasswordUiState(

    val email: String = "",

    val emailError: String? = null,

    val isLoading: Boolean = false,

    val error: String? = null

) {

    val isFormValid: Boolean
        get() =
            email.isNotBlank() &&
                    emailError == null
}