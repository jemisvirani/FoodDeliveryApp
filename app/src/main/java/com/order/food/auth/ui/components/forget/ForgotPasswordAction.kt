package com.order.food.auth.ui.components.forget

sealed interface ForgotPasswordAction {

    data class EmailChanged(
        val value: String
    ) : ForgotPasswordAction

    data object SendResetLinkClicked : ForgotPasswordAction
}