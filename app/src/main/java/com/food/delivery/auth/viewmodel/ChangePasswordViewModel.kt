package com.food.delivery.auth.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.food.delivery.auth.domain.repository.AuthRepository
import com.food.delivery.auth.ui.state.ChangePasswordUiState
import com.food.delivery.auth.validation.AuthValidation
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class ChangePasswordViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    private val _state = MutableStateFlow(ChangePasswordUiState())
    val state = _state.asStateFlow()

    fun onPasswordChange(value: String) {

        _state.update {
            it.copy(
                newPassword = value,
                newPasswordError = null
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

    fun changePassword(
        onSuccess: () -> Unit
    ) {
        val ui = state.value

        val passwordError = AuthValidation.password(ui.newPassword)
        val confirmError = AuthValidation.confirmPassword(
            ui.newPassword,
            ui.confirmPassword
        )

        _state.update {
            it.copy(
                newPasswordError = passwordError,
                confirmPasswordError = confirmError
            )
        }

        if (passwordError != null || confirmError != null) return

        viewModelScope.launch {

            _state.update { it.copy(isLoading = true) }

            repository.changePassword(ui.newPassword)
                .onSuccess {

                    _state.update {
                        it.copy(
                            isLoading = false,
                            snackBar = "Password changed successfully."
                        )
                    }

                    onSuccess()
                }
                .onFailure { e ->

                    _state.update {
                        it.copy(
                            isLoading = false,
                            snackBar = e.message ?: "Failed to change password."
                        )
                    }
                }
        }
    }
}