package com.order.food.auth.ui.components.changepass

sealed interface ChangePasswordAction {

    data class PasswordChanged(val value: String) : ChangePasswordAction

    data class ConfirmPasswordChanged(val value: String) : ChangePasswordAction

    data object TogglePassword : ChangePasswordAction

    data object ToggleConfirmPassword : ChangePasswordAction

    data object ChangePasswordClicked : ChangePasswordAction
}