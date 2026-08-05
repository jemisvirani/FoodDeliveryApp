package com.order.food.auth.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.order.food.auth.domain.repository.AuthRepository
import com.order.food.auth.ui.components.changepass.ChangePasswordAction
import com.order.food.auth.ui.components.changepass.ChangePasswordEvent
import com.order.food.auth.ui.components.changepass.ChangePasswordUiState
import com.order.food.auth.validation.AuthValidation
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class ChangePasswordViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ChangePasswordUiState())
    val uiState: StateFlow<ChangePasswordUiState> =
        _uiState.asStateFlow()

    private val _event = MutableSharedFlow<ChangePasswordEvent>()
    val event = _event.asSharedFlow()

    fun onAction(action: ChangePasswordAction) {
        when (action) {

            is ChangePasswordAction.PasswordChanged ->
                updatePassword(action.value)

            is ChangePasswordAction.ConfirmPasswordChanged ->
                updateConfirmPassword(action.value)

            ChangePasswordAction.TogglePassword ->
                togglePasswordVisibility()

            ChangePasswordAction.ToggleConfirmPassword ->
                toggleConfirmPasswordVisibility()

            ChangePasswordAction.ChangePasswordClicked ->
                changePassword()
        }
    }

    private fun updatePassword(value: String) {

        _uiState.update {

            it.copy(
                password = value,
                passwordError = AuthValidation.validatePassword(value),
                passwordStrength = AuthValidation.passwordStrength(value),
                confirmPasswordError =
                    AuthValidation.validateConfirmPassword(
                        value,
                        it.confirmPassword
                    )
            )
        }
    }

    private fun updateConfirmPassword(value: String) {

        _uiState.update {

            it.copy(
                confirmPassword = value,
                confirmPasswordError =
                    AuthValidation.validateConfirmPassword(
                        it.password,
                        value
                    )
            )
        }
    }

    private fun togglePasswordVisibility() {
        _uiState.update {
            it.copy(
                isPasswordVisible = !it.isPasswordVisible
            )
        }
    }

    private fun toggleConfirmPasswordVisibility() {
        _uiState.update {
            it.copy(
                isConfirmPasswordVisible = !it.isConfirmPasswordVisible
            )
        }
    }

    private fun changePassword() {

        val state = _uiState.value

        val passwordError =
            AuthValidation.validatePassword(state.password)

        val confirmPasswordError =
            AuthValidation.validateConfirmPassword(
                state.password,
                state.confirmPassword
            )

        if (
            passwordError != null ||
            confirmPasswordError != null
        ) {

            _uiState.update {

                it.copy(
                    passwordError = passwordError,
                    confirmPasswordError = confirmPasswordError
                )
            }

            return
        }

        viewModelScope.launch {

            _uiState.update {

                it.copy(
                    isLoading = true,
                    error = null
                )
            }

            repository.changePassword(
                password = state.password
            ).onSuccess {

                _uiState.update {

                    it.copy(
                        isLoading = false
                    )
                }

                _event.emit(
                    ChangePasswordEvent.ShowSnackbar(
                        "Password changed successfully."
                    )
                )

                _event.emit(
                    ChangePasswordEvent.NavigateToLogin
                )

            }.onFailure { throwable ->

                _uiState.update {

                    it.copy(
                        isLoading = false
                    )
                }

                _event.emit(
                    ChangePasswordEvent.ShowSnackbar(
                        throwable.message
                            ?: "Something went wrong"
                    )
                )
            }
        }
    }
}