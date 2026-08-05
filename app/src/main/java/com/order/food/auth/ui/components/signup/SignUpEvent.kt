package com.order.food.auth.ui.components.signup

sealed interface SignUpEvent {

    data object NavigateToHome : SignUpEvent

    data object NavigateToLogin : SignUpEvent

    data class ShowSnackbar(
        val message: String
    ) : SignUpEvent
}