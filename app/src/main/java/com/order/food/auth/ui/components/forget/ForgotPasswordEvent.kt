package com.order.food.auth.ui.components.forget

sealed interface ForgotPasswordEvent {

    data object NavigateToLogin : ForgotPasswordEvent

    data class ShowSnackbar(
        val message: String
    ) : ForgotPasswordEvent
}