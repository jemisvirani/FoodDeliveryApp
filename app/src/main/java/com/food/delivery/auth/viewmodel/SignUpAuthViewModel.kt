package com.food.delivery.auth.viewmodel

import androidx.lifecycle.ViewModel
import com.food.delivery.auth.ui.state.SignUpUiState
import com.food.delivery.auth.validation.AuthValidation
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

@HiltViewModel
class SignUpViewModel @Inject constructor() : ViewModel() {

    private val _state =
        MutableStateFlow(SignUpUiState())

    val state = _state.asStateFlow()

    fun onNameChange(value: String) {

        _state.update {
            it.copy(
                fullName = value,
                fullNameError = null
            )
        }

    }

    fun onEmailChange(value: String) {

        _state.update {
            it.copy(
                email = value,
                emailError = null
            )
        }

    }

    fun onAddressChange(value: String) {

        _state.update {
            it.copy(
                address = value,
                addressError = null
            )
        }

    }

    fun onPasswordChange(value: String) {

        _state.update {
            it.copy(
                password = value,
                passwordError = null
            )
        }

    }

    fun onConfirmPasswordChange(value: String) {

        _state.update {
            it.copy(
                confirmPassword = value,
                confirmPasswordError = null
            )
        }

    }

    fun signUp(
        onSuccess: () -> Unit
    ) {

        val ui = state.value

        val nameError = AuthValidation.name(ui.fullName)
        val emailError = AuthValidation.email(ui.email)
        val addressError = AuthValidation.address(ui.address)
        val passwordError = AuthValidation.password(ui.password)
        val confirmError =
            AuthValidation.confirmPassword(
                ui.password,
                ui.confirmPassword
            )

        _state.update {

            it.copy(

                fullNameError = nameError,

                emailError = emailError,

                addressError = addressError,

                passwordError = passwordError,

                confirmPasswordError = confirmError

            )

        }

        if (
            nameError == null &&
            emailError == null &&
            addressError == null &&
            passwordError == null &&
            confirmError == null
        ) {

            onSuccess()

        }

    }

}