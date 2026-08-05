package com.order.food.auth.validation

import com.order.food.auth.ui.components.signup.PasswordStrength

object AuthValidation {

    fun validateName(name: String): String? {

        return when {

            name.isBlank() ->
                "Full name is required"

            name.length < 3 ->
                "Minimum 3 characters"

            else -> null
        }

    }

    fun validateEmail(email: String): String? {
        val value = email.trim()

        val emailRegex = Regex(
            "^[A-Za-z0-9._%+-]+@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)+$"
        )

        return when {
            value.isBlank() -> "Email is required"
            !emailRegex.matches(value) -> "Invalid email"
            else -> null
        }
    }

    fun validateAddress(address: String): String? {

        return when {

            address.isBlank() ->
                "Address is required"

            address.length < 5 ->
                "Address is too short"

            else -> null
        }

    }

    fun validatePassword(password: String): String? {

        return when {

            password.isBlank() ->
                "Password is required"

            password.length < 8 ->
                "Minimum 8 characters"

            !password.any { it.isUpperCase() } ->
                "Must contain uppercase letter"

            !password.any { it.isLowerCase() } ->
                "Must contain lowercase letter"

            !password.any { it.isDigit() } ->
                "Must contain number"

            else ->
                null

        }

    }

    fun validateConfirmPassword(
        password: String,
        confirmPassword: String
    ): String? {

        return when {

            confirmPassword.isBlank() ->
                "Confirm your password"

            password != confirmPassword ->
                "Passwords do not match"

            else ->
                null

        }

    }

    fun passwordStrength(password: String): PasswordStrength {

        if (password.isBlank())
            return PasswordStrength.NONE

        var score = 0

        if (password.length >= 8) score++
        if (password.any { it.isUpperCase() }) score++
        if (password.any { it.isLowerCase() }) score++
        if (password.any { it.isDigit() }) score++
        if (password.any { !it.isLetterOrDigit() }) score++

        return when {

            score <= 2 ->
                PasswordStrength.WEAK

            score <= 4 ->
                PasswordStrength.MEDIUM

            else ->
                PasswordStrength.STRONG

        }

    }

}