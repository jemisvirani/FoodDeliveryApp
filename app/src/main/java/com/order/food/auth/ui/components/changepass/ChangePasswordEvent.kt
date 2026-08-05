package com.order.food.auth.ui.components.changepass

sealed interface ChangePasswordEvent {

    data object NavigateToLogin : ChangePasswordEvent

    data class ShowSnackbar(
        val message: String
    ) : ChangePasswordEvent
}