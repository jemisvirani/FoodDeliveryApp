package com.order.food.auth.ui.components.login

sealed interface LoginAction {

    data class EmailChanged(val value: String) : LoginAction

    data class PasswordChanged(val value: String) : LoginAction

    data object TogglePassword : LoginAction

    data class RememberMeChanged(val checked: Boolean) : LoginAction

    data object LoginClicked : LoginAction

    data object ForgotPasswordClicked : LoginAction

    data object SignUpClicked : LoginAction
}