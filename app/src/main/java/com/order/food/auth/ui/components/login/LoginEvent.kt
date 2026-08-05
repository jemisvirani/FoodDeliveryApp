package com.order.food.auth.ui.components.login

sealed interface LoginEvent {

    data object NavigateToHome : LoginEvent

    data object NavigateToForgotPassword : LoginEvent

    data object NavigateToSignUp : LoginEvent

    data class ShowSnackbar(
        val message: String
    ) : LoginEvent
}