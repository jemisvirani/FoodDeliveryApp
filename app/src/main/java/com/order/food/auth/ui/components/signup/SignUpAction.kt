package com.order.food.auth.ui.components.signup

sealed interface SignUpAction {

    data class FullNameChanged(val value: String) : SignUpAction

    data class EmailChanged(val value: String) : SignUpAction

    data class AddressChanged(val value: String) : SignUpAction

    data class PasswordChanged(val value: String) : SignUpAction

    data class ConfirmPasswordChanged(val value: String) : SignUpAction

    data object TogglePassword : SignUpAction

    data object ToggleConfirmPassword : SignUpAction

    data object SignUpClicked : SignUpAction

    data object LoginClicked : SignUpAction
}