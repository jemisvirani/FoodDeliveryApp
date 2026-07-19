package com.food.delivery.auth.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.food.delivery.auth.domain.repository.AuthRepository
import com.food.delivery.auth.ui.state.ForgotPasswordUiState
import com.food.delivery.auth.validation.AuthValidation
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(private val repository: AuthRepository) : ViewModel() {

    private val _state = MutableStateFlow(ForgotPasswordUiState())
    val state = _state.asStateFlow()

    fun onEmailChange(value: String) {
        _state.update {
            it.copy(
                email = value,
                emailError = null
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



    fun forgotPassword(
    ) {
        if (state.value.isLoading) return

        val currentEmail = state.value.email
        val emailError = AuthValidation.email(currentEmail)

        _state.update {
            it.copy(emailError = emailError)
        }

        if (emailError != null) return

        viewModelScope.launch {

            _state.update { it.copy(isLoading = true) }

            try {

                val exists = repository.isUserRegistered(state.value.email)

                if (!exists) {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            snackBar = "Email is not registered."
                        )
                    }
                    return@launch
                }

                repository.forgotPassword(state.value.email)
                    .onSuccess {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                snackBar = "Password reset email sent. Please check your inbox."
                            )
                        }
                    }

            } catch (e: Exception) {

                _state.update {
                    it.copy(
                        isLoading = false,
                        snackBar = e.message ?: "Something went wrong."
                    )
                }
            }
        }
    }
}