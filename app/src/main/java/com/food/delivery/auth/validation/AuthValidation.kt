package com.food.delivery.auth.validation

import android.util.Patterns

object AuthValidation {

    fun name(name: String): String? {

        return when {

            name.isBlank() ->
                "Name is required"

            name.length < 3 ->
                "Minimum 3 characters"

            else -> null
        }
    }

    fun email(email: String): String? {

        return when {

            email.isBlank() ->
                "Email is required"

            !Patterns.EMAIL_ADDRESS.matcher(email).matches() ->
                "Invalid email"

            else -> null
        }
    }

    fun address(address: String): String? {

        return if (address.isBlank())
            "Address is required"
        else
            null
    }

    fun password(password: String): String? {

        val regex =
            Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$")

        return when {

            password.isBlank() ->
                "Password is required"

            !regex.matches(password) ->
                "Min 8 chars, Uppercase, Lowercase & Number"

            else -> null
        }
    }

    fun confirmPassword(
        password: String,
        confirm: String
    ): String? {

        return when {

            confirm.isBlank() ->
                "Confirm password required"

            password != confirm ->
                "Password doesn't match"

            else -> null
        }
    }

}