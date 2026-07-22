package com.food.delivery.auth.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.food.delivery.auth.domain.repository.AuthRepository
import com.food.delivery.auth.ui.state.SignUpUiState
import com.food.delivery.auth.validation.AuthValidation
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class SignUpViewModel @Inject constructor(private val repository: AuthRepository
) : ViewModel() {

    private val _snackBarEvent = MutableSharedFlow<String>()
    val snackBarEvent = _snackBarEvent.asSharedFlow()

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

    fun clearSnackBar() {
        _state.update {
            it.copy(
                snackBar = null
            )
        }
    }

    fun signUp(onSuccess: () -> Unit) {

        val ui = state.value

        val fullNameError = AuthValidation.name(ui.fullName)
        val emailError = AuthValidation.email(ui.email)
        val addressError = AuthValidation.address(ui.address)
        val passwordError = AuthValidation.password(ui.password)
        val confirmPasswordError =
            AuthValidation.confirmPassword(
                ui.password,
                ui.confirmPassword
            )

        _state.update {
            it.copy(
                fullNameError = fullNameError,
                emailError = emailError,
                addressError = addressError,
                passwordError = passwordError,
                confirmPasswordError = confirmPasswordError
            )
        }

        val validationMessage = when {
            fullNameError != null -> fullNameError
            emailError != null -> emailError
            addressError != null -> addressError
            passwordError != null -> passwordError
            confirmPasswordError != null -> confirmPasswordError
            else -> null
        }

        if (validationMessage != null) {
            viewModelScope.launch {
                _snackBarEvent.emit(validationMessage)
            }
            return
        }

        viewModelScope.launch {

            _state.update { it.copy(isLoading = true) }

            repository.signUp(
                fullName = ui.fullName,
                email = ui.email,
                address = ui.address,
                password = ui.password
            ).onSuccess {

                _state.update {
                    it.copy(isLoading = false)
                }

                onSuccess()

            }.onFailure { exception ->

                _state.update {
                    it.copy(isLoading = false)
                }

                _snackBarEvent.emit(
                    exception.message ?: "Sign Up failed"
                )
            }
        }
    }
}