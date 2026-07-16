package com.food.delivery.auth.viewmodel

import androidx.lifecycle.ViewModel
import com.food.delivery.auth.ui.state.ChangePasswordUiState
import com.food.delivery.auth.validation.AuthValidation
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

@HiltViewModel
class ChangePasswordViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(ChangePasswordUiState())
    val state = _state.asStateFlow()

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

    fun changePassword(
        onSuccess: () -> Unit
    ) {

        val ui = state.value

        val passwordError = AuthValidation.password(ui.password)

        val confirmPasswordError =
            AuthValidation.confirmPassword(
                ui.password,
                ui.confirmPassword
            )

        _state.update {
            it.copy(
                passwordError = passwordError,
                confirmPasswordError = confirmPasswordError
            )
        }

        if (passwordError == null &&
            confirmPasswordError == null
        ) {
            onSuccess()
        }
    }
}